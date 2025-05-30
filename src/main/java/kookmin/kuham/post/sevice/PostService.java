package kookmin.kuham.post.sevice;

import kookmin.kuham.portfolio.service.PortfolioService;
import kookmin.kuham.post.dto.request.SavePostRequest;
import kookmin.kuham.post.dto.response.getPostsResponse;
import kookmin.kuham.post.exception.PostNotFoundException;
import kookmin.kuham.post.repository.PostRepository;
import kookmin.kuham.post.schema.Post;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PortfolioService portfolioService;

    public void addPost(SavePostRequest savePostRequest, MultipartFile[] images,String userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        Post newPost = Post.builder()
                .title(savePostRequest.title())
                .description(savePostRequest.description())
                .domain(savePostRequest.domain())
                .startDate(savePostRequest.startDate())
                .endDate(savePostRequest.endDate())
                .maxMember(savePostRequest.maxMember())
                .roles(savePostRequest.roles())
                .preferredCharacters(savePostRequest.preferredCharacters())
                .stacks(savePostRequest.stacks())
                .user(user)
                .build();

        user.getPosts().add(newPost);
        postRepository.save(newPost);

        newPost.setImages(portfolioService.uploadImage(userId, newPost.getId(), "posts",images));
        postRepository.save(newPost);

    }

    public void updatePost(SavePostRequest savePostRequest, MultipartFile[] images, Long postId) throws IOException{
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        Post post = user.getPosts().stream().filter(p-> Objects.equals(p.getId(),postId)).findFirst().orElseThrow(PostNotFoundException::new);
        post.setTitle(savePostRequest.title());
        post.setDescription(savePostRequest.description());
        post.setDomain(savePostRequest.domain());
        post.setStartDate(savePostRequest.startDate());
        post.setEndDate(savePostRequest.endDate());
        post.setMaxMember(savePostRequest.maxMember());
        post.setRoles(savePostRequest.roles());
        post.setPreferredCharacters(savePostRequest.preferredCharacters());
        post.setStacks(savePostRequest.stacks());

        portfolioService.deleteImage(userId, post.getId(), "posts");
        post.setImages(portfolioService.uploadImage(userId, post.getId(), "posts",images));

        postRepository.save(post);
    }

    public void deletePost(Long postId){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        Post post = user.getPosts().stream().filter(p-> Objects.equals(p.getId(),postId)).findFirst().orElseThrow(PostNotFoundException::new);

        portfolioService.deleteImage(userId, post.getId(), "posts");
        user.getPosts().remove(post);
        postRepository.delete(post);
    }

    public List<getPostsResponse> getPosts(String userId){
        // 만약 userId가 넘어온 경우 해당 유저의 공고를 가져옴
        if (userId != null) {
            return postRepository.findAllByUserId(userId).stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .map(post -> new getPostsResponse(
                            post.getId(),
                            post.getTitle(),
                            post.getDescription(),
                            post.getDomain(),
                            post.getCreatedAt(),
                            post.getUser().getName(),
                            post.getUser().getMajor()
                    )).toList();
        } else {
            // 만약 userId가 넘어오지 않은 경우 모든 공고를 가져옴
            return postRepository.findAll().stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .map(post -> new getPostsResponse(
                            post.getId(),
                            post.getTitle(),
                            post.getDescription(),
                            post.getDomain(),
                            post.getCreatedAt(),
                            post.getUser().getName(),
                            post.getUser().getMajor()
                    )).toList();

        }

    }

}

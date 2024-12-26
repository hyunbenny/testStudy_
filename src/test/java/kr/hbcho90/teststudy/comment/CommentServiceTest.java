package kr.hbcho90.teststudy.comment;

import kr.hbcho90.teststudy.post.PostEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("댓글기능 테스트")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {


    @InjectMocks
    private CommentService sut;
    @Mock
    private CommentRepository commentRepository;


    @Test
    public void test() {
        PostEntity postEntity = PostEntity.builder()
                .title("this is title")
                .content("hello world. this is content")
                .build();

        CommentEntity comment = CommentEntity.builder()
                .comment("this is comment")
                .createdBy("hbcho90")
                .post(postEntity)
                .build();


        System.out.println(comment);
        System.out.println(comment.getPost());
    }

}
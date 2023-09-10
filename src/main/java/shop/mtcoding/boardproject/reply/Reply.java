package shop.mtcoding.boardproject.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.master.Master;

import javax.persistence.*;
import java.sql.Timestamp;

// 문의에 대한 답변 테이블
@NoArgsConstructor
@Setter
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Master master;

    private Timestamp createdAt;

    @Builder
    public Reply(Integer id, String content, Master master, Timestamp createdAt) {
        this.id = id;
        this.content = content;
        this.master = master;
        this.createdAt = createdAt;
    }

}

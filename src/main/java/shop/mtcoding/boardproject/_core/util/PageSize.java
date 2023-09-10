package shop.mtcoding.boardproject._core.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PageSize {

    private int mainPageSize = 4;

    private int searchAllPageSize = 4;

}

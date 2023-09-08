package shop.mtcoding.boardproject._core.util;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PageSize {

    private int mainPageSize = 4;

    private int searchAllPageSize = 4;
    
}

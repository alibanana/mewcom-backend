package com.mewcom.backend.rest.web.util;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.exception.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Component
public class PageUtil {

  public static PageRequest validateAndGetPageRequest(Integer page, Integer size, String orderBy,
      String sortBy) {
    page = validateAndInitializePageNumber(page);
    size = validateAndInitializePageSize(size);
    validateSortByAndOrderBy(sortBy, orderBy);
    return getPageRequest(page, size, orderBy, sortBy);
  }

  private static int validateAndInitializePageNumber(Integer page) {
    if (Objects.nonNull(page)) {
      if (page < 0) {
        throw new BaseException(ErrorCode.PAGE_NUMBER_LESS_THAN_ZERO);
      }
      return page;
    }
    return 0;
  }

  private static int validateAndInitializePageSize(Integer size) {
    if (Objects.nonNull(size)) {
      if (size <= 0) {
        throw new BaseException(ErrorCode.PAGE_SIZE_LESS_THAN_OR_EQUAL_TO_ZERO);
      }
      return size;
    }
    return 10;
  }

  private static void validateSortByAndOrderBy(String sortBy, String orderBy) {
    if (Objects.nonNull(sortBy) && !sortBy.equals(ASC.name()) && !sortBy.equals(DESC.name())) {
      throw new BaseException(ErrorCode.SORT_BY_VALUES_INVALID);
    }

    if ((Objects.nonNull(sortBy) && Objects.isNull(orderBy)) || (Objects.isNull(sortBy) &&
        Objects.nonNull(orderBy))) {
      throw new BaseException(ErrorCode.SORT_BY_AND_ORDER_BY_MUST_BOTH_EXISTS);
    }
  }

  private static PageRequest getPageRequest(Integer page, Integer size, String orderBy,
      String sortBy) {
    if (Objects.isNull(orderBy) && Objects.isNull(sortBy)) {
      return PageRequest.of(page, size);
    } else {
      return PageRequest.of(page, size, Sort.Direction.fromString(sortBy), orderBy);
    }
  }
}

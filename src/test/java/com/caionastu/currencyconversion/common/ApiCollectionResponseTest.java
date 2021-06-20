package com.caionastu.currencyconversion.common;

import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class ApiCollectionResponseTest {

    @Test
    @DisplayName("It should create ApiCollectionResponse with hasNext true.")
    void hasNextTrue() {
        PageImpl<String> page = getPage(10, 10, 20, 0);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.hasNext()).isTrue();
    }

    @Test
    @DisplayName("It should create ApiCollectionResponse with hasNext false.")
    void hasNextFalse() {
        PageImpl<String> page = getPage(5, 10, 5, 0);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.hasNext()).isFalse();
    }

    @Test
    @DisplayName("It should create ApiCollectionResponse with same items size as in page.")
    void sameItemsSize() {
        int size = 5;
        PageImpl<String> page = getPage(size, 10, 20, 0);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.getItems()).hasSize(size);
    }

    @Test
    @DisplayName("It should create ApiCollectionResponse with same page size as in page.")
    void samePageSize() {
        int pageSize = 10;
        PageImpl<String> page = getPage(5, pageSize, 20, 0);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.getPageSize()).isEqualTo(pageSize);
    }

    @Test
    @DisplayName("It should create ApiCollectionResponse with same page number as in page.")
    void samePageNumber() {
        int pageNumber = 2;
        PageImpl<String> page = getPage(5, 5, 20, pageNumber);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.getPageNumber()).isEqualTo(pageNumber);
    }

    @Test
    @DisplayName("It should create ApiCollectionResponse with same total elements as in page.")
    void sameTotalElements() {
        int totalElements = 30;
        PageImpl<String> page = getPage(5, 5, totalElements, 0);

        ApiCollectionResponse<String> response = ApiCollectionResponse.from(page);

        assertThat(response.getTotalElements()).isEqualTo(totalElements);
    }

    private PageImpl<String> getPage(int itemsSize, int pageSize, int totalElements, int pageNumber) {
        List<String> objects = getListOfSize(itemsSize);
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        return new PageImpl<>(objects, pageable, totalElements);
    }

    private List<String> getListOfSize(int size) {
        List<String> objects = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            objects.add("Item " + (i + 1));
        }

        return objects;
    }
}

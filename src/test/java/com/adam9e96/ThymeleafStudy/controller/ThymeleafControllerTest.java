package com.adam9e96.ThymeleafStudy.controller;

import com.adam9e96.ThymeleafStudy.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.ui.ModelMap;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ThymeleafControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    @DisplayName("/show 요청 시 useThymeleaf 뷰와 모델 데이터 검증")
    void testShowView() throws Exception {
        // /show 요청 시 useThymeleaf 뷰를 반환하며, 모델에 name, mb, list, map, members 속성이 존재하는지 검증
        MvcResult mvcResult = mockMvc.perform(get("/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("useThymeleaf"))
                .andReturn();

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        // name 검증
        assertThat(modelMap.get("name")).isEqualTo("이순신");

        // mb 검증
        assertThat(modelMap.get("mb")).isInstanceOf(Member.class);
        Member mb = (Member) modelMap.get("mb");
        assertThat(mb.getId()).isEqualTo(1);
        assertThat(mb.getName()).isEqualTo("회원01");

        // list 검증
        assertThat(modelMap.get("list")).isInstanceOf(List.class);
        List<String> directionList = (List<String>) modelMap.get("list");
        assertThat(directionList).containsExactly("left", "right", "up", "down");


        // map 검증
        assertThat(modelMap.get("map")).isInstanceOf(Map.class);
        Map<?, ?> memberMap = (Map<?, ?>) modelMap.get("map");
        assertThat(memberMap).hasSize(2);
        assertThat(memberMap.containsKey("hong")).isTrue();
        assertThat(memberMap.containsKey("lee")).isTrue();
        assertThat(memberMap.get("hong")).isInstanceOf(Member.class);
        Member hong = (Member) memberMap.get("hong");
        assertThat(hong.getId()).isEqualTo(10);
        assertThat(hong.getName()).isEqualTo("홍길동");

        // members 검증
        assertThat(modelMap.get("members")).isInstanceOf(List.class);
        List<?> members = (List<?>) modelMap.get("members");
        assertThat(members).hasSize(2);

        Member member1 = (Member) members.get(0);
        assertThat(member1.getName()).isEqualTo("홍길동");
        assertThat(member1.getId()).isEqualTo(10);

        Member member2 = (Member) members.get(1);
        assertThat(member2.getName()).isEqualTo("이영희");
        assertThat(member2.getId()).isEqualTo(20);
    }

    @Test
    @DisplayName("/a 요청 시 pageA 뷰 반환 검증")
    void testShowA() throws Exception {
        mockMvc.perform(get("/a"))
                .andExpect(status().isOk())
                .andExpect(view().name("pageA"));
    }
}

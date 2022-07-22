/*
 * Copyright (c) 2022. Edirin T. Atumah
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bongosway.io.realworldapp.acceptance;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bongosway.io.realworldapp.domain.user.application.UserController;
import com.bongosway.io.realworldapp.domain.user.core.UserFacade;
import com.bongosway.io.realworldapp.domain.user.core.model.UpdateUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UserAggregate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserFacade mockUserFacade;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void whenValidUpdateInput_thenReturns200() throws Exception {
    var userAggregate = UserAggregate.builder().username("alex").email("testemail@testemail.com")
        .build();
    var updateRequest = new UpdateUserRequest("testemail@testemail.com", "test bio", "test-image");

    when(mockUserFacade.handle(any(UpdateUserRequest.class))).thenReturn(userAggregate);

    put(updateRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.username", is(userAggregate.getUsername())))
        .andExpect(jsonPath("$.user.email", is(updateRequest.getEmail())));
  }

  @Test
  void whenNotValidInput_thenReturns400() throws Exception {
    var invalidRequest = new UpdateUserRequest("", "test bio", "test-image");
    put(invalidRequest).andExpect(status().isBadRequest());
  }

  private ResultActions put(Object request) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.put("/api/user")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(request)));
  }
}

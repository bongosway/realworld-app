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

import com.bongosway.io.realworldapp.domain.user.application.UsersController;
import com.bongosway.io.realworldapp.domain.user.core.UserFacade;
import com.bongosway.io.realworldapp.domain.user.core.model.NewUserRequest;
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

@WebMvcTest(controllers = UsersController.class)
class UsersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserFacade mockUserFacade;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void whenValidRegisterInput_thenReturns200() throws Exception {
    var userAggregate = UserAggregate.builder().username("alex").email("a@a.com").build();
    var newRequest = new NewUserRequest("alex", "a@a.com", "password");

    when(mockUserFacade.handle(any(NewUserRequest.class))).thenReturn(userAggregate);

    post(newRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.username", is(userAggregate.getUsername())))
        .andExpect(jsonPath("$.user.email", is(userAggregate.getEmail())));
  }

  @Test
  void whenNotValidRegisterInput_thenReturns400() throws Exception {
    var invalidRequest = new NewUserRequest("", "", "");
    post(invalidRequest).andExpect(status().isBadRequest());
  }

  private ResultActions post(Object request) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(request)));
  }
}

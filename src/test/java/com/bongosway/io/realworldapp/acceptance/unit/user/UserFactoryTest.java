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

package com.bongosway.io.realworldapp.acceptance.unit.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.bongosway.io.realworldapp.domain.user.core.model.NewUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UpdateUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UserEntity;
import com.bongosway.io.realworldapp.domain.user.core.model.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFactoryTest {

  private NewUserRequest newUserRequest;
  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    newUserRequest = new NewUserRequest("a", "a@a.com", "pass");
    testUser = UserFactory.createNewUser(newUserRequest);
  }

  @Test
  void canConstructValidUserEntity() {
    UserEntity user = UserFactory.createNewUser(newUserRequest);

    assertThat(user).isNotNull();
    assertThat(user.getEmail()).isEqualTo(newUserRequest.getEmail());
    assertThat(user.getUsername()).isEqualTo(newUserRequest.getUsername());
  }

  @Test
  void canUpdateExistingUserEntity() {
    UpdateUserRequest request = new UpdateUserRequest("test@test.com", "I love test",
        "http://image.com");

    UserFactory.updateUser(testUser, request);

    assertThat(testUser).isNotNull();
    assertThat(testUser.getBio()).isEqualTo(request.getBio());
    assertThat(testUser.getImage()).isEqualTo(request.getImage());
  }

  @Test
  void willOnlyUpdateUserEntityWithProvidedFields() {
    UpdateUserRequest request = new UpdateUserRequest("a@a.com", null, "");

    UserFactory.updateUser(testUser, request);

    assertThat(testUser).isNotNull();
    assertThat(testUser.getBio()).isEqualTo(testUser.getBio());
    assertThat(testUser.getImage()).isEqualTo(testUser.getImage());
  }
}

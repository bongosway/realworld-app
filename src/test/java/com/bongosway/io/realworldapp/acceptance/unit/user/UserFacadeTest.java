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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bongosway.io.realworldapp.domain.user.core.UserFacade;
import com.bongosway.io.realworldapp.domain.user.core.model.exception.DuplicateUserFoundException;
import com.bongosway.io.realworldapp.domain.user.core.model.NewUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UpdateUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UserAggregate;
import com.bongosway.io.realworldapp.domain.user.core.model.UserEntity;
import com.bongosway.io.realworldapp.domain.user.core.model.exception.UserNotFoundException;
import com.bongosway.io.realworldapp.domain.user.core.port.out.UserDao;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFacadeTest {

  private UserDao mockUserDao;
  private UserEntity testUser;
  private NewUserRequest newUserRequest;
  private UpdateUserRequest updateRequest;
  private UserFacade userFacade;

  @BeforeEach
  void setUp() {
    updateRequest = new UpdateUserRequest("test-update@test.com", "Real Engineer", "https://image.com/user1");
    newUserRequest = new NewUserRequest("test-user", "test@email.com", "test-password");
    testUser = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .username("asdsdsa")
        .token("random token")
        .email("a@a.com")
        .password("pass")
        .build();

    mockUserDao = mock(UserDao.class);
    userFacade = new UserFacade(mockUserDao);
  }

  @Test
  void shouldSaveUserAndReturnAggregate() {
    UserAggregate expected = UserAggregate.builder()
        .username(newUserRequest.getUsername())
        .email(newUserRequest.getEmail())
        .build();

    UserAggregate response = userFacade.handle(newUserRequest);
    verify(mockUserDao).save(any(UserEntity.class));

    assertThat(response).isNotNull();
    assertThat(response.getEmail()).isEqualTo(expected.getEmail());
    assertThat(response.getBio()).isNull();
    assertThat(response.getToken()).isNull();
  }

  @Test
  void shouldUpdateUser() {
    when(mockUserDao.findByEmail(updateRequest.getEmail())).thenReturn(Optional.of(testUser));
    UserAggregate response = userFacade.handle(updateRequest);

    assertThat(response).usingRecursiveComparison()
        .ignoringFields("username", "token")
        .isEqualTo(updateRequest);
  }

  @Test
  void shouldNotSaveDuplicateUser() {
    when(mockUserDao.findByEmail(newUserRequest.getEmail())).thenReturn(Optional.of(testUser));
    assertThatThrownBy(() -> userFacade.handle(newUserRequest))
        .isInstanceOf(DuplicateUserFoundException.class)
        .hasMessage("Member profile with email <%s> exist", newUserRequest.getEmail());
  }

  @Test
  void shouldNotUpdateUnknownUser() {
    assertThatThrownBy(() -> userFacade.handle(updateRequest))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("Member profile with email <%s> does not exist", updateRequest.getEmail());
  }
}

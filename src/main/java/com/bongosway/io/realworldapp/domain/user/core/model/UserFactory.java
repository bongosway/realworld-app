/*
 * Copyright (c) 2022-2022. Edirin T. Atumah
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

package com.bongosway.io.realworldapp.domain.user.core.model;

import java.util.UUID;

public class UserFactory {

  private UserFactory() {
  }

  public static UserEntity createNewUser(NewUserRequest userRequest) {
    return UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .username(userRequest.getUsername())
        .password(userRequest.getPassword())
        .email(userRequest.getEmail())
        .build();
  }

  public static UserEntity updateUser(UserEntity foundUser, UpdateUserRequest userRequest) {
    foundUser.setEmail(userRequest.getEmail());

    if (notNullOrEmpty(userRequest.getBio())) {
      foundUser.setBio(userRequest.getBio());
    }

    if (notNullOrEmpty(userRequest.getImage())) {
      foundUser.setImage(userRequest.getImage());
    }

    return foundUser;
  }

  private static boolean notNullOrEmpty(String value) {
    return value != null && !value.isEmpty();
  }
}

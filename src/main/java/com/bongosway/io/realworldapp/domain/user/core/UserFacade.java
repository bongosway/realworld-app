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

package com.bongosway.io.realworldapp.domain.user.core;

import com.bongosway.io.realworldapp.domain.user.core.model.RegisterUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UpdateUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.User;
import com.bongosway.io.realworldapp.domain.user.core.model.UserAggregate;
import com.bongosway.io.realworldapp.domain.user.core.model.UserFactory;
import com.bongosway.io.realworldapp.domain.user.core.port.in.RegisterUseCase;
import com.bongosway.io.realworldapp.domain.user.core.port.in.UpdateUseCase;
import com.bongosway.io.realworldapp.domain.user.core.port.out.UserDao;

public class UserFacade implements RegisterUseCase, UpdateUseCase {

  private final UserDao userDatabase;

  public UserFacade(UserDao userDatabase) {
    this.userDatabase = userDatabase;
  }

  @Override
  public UserAggregate handle(RegisterUserRequest registerUserRequest) {
    User newUser = UserFactory.createNewUser(registerUserRequest);
    userDatabase.save(newUser);

    return convertToAggregate(newUser);
  }

  @Override
  public UserAggregate handle(UpdateUserRequest updateUserRequest) {
    User foundUser = userDatabase.findByEmail(updateUserRequest.getEmail()).orElseThrow();
    User updatedUser = UserFactory.updateUser(foundUser, updateUserRequest);
    userDatabase.save(updatedUser);

    return convertToAggregate(updatedUser);
  }

  private static UserAggregate convertToAggregate(User that) {
    return new UserAggregate(
        that.getUsername(),
        that.getEmail(),
        that.getToken(),
        that.getBio(),
        that.getImage()
    );
  }
}

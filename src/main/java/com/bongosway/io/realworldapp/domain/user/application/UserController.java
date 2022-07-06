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

package com.bongosway.io.realworldapp.domain.user.application;

import com.bongosway.io.realworldapp.domain.user.core.model.RegisterUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UpdateUserRequest;
import com.bongosway.io.realworldapp.domain.user.core.model.UserAggregate;
import com.bongosway.io.realworldapp.domain.user.core.port.in.RegisterUseCase;
import com.bongosway.io.realworldapp.domain.user.core.port.in.UpdateUseCase;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final RegisterUseCase registerUseCase;
  private final UpdateUseCase updateUseCase;

  public UserController(RegisterUseCase registerUseCase, UpdateUseCase updateUseCase) {
    this.registerUseCase = registerUseCase;
    this.updateUseCase = updateUseCase;
  }

  @PostMapping(value = "/api/users")
  public ResponseEntity<UserAggregate> createUser(
      @RequestBody @Valid RegisterUserRequest registerUserRequest) {

    return ResponseEntity.ok(registerUseCase.handle(registerUserRequest));
  }

  @PutMapping(value = "/api/users")
  public ResponseEntity<UserAggregate> updateUser(
      @RequestBody @Valid UpdateUserRequest updateUserRequest) {
    return ResponseEntity.ok(updateUseCase.handle(updateUserRequest));
  }
}

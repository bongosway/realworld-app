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

package com.bongosway.io.realworldapp.domain.user.adapter.out;

import com.bongosway.io.realworldapp.domain.user.core.model.UserEntity;
import com.bongosway.io.realworldapp.domain.user.core.port.out.UserDao;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class JpaUserDaoAdapter implements UserDao {

  private final UserRepository userRepository;

  public JpaUserDaoAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public void save(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  @Transactional
  public Optional<UserEntity> findById(String id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}

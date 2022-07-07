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

package com.bongosway.io.realworldapp.acceptance;

import com.bongosway.io.realworldapp.domain.user.core.model.User;
import com.bongosway.io.realworldapp.domain.user.core.port.out.UserDao;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTestUserDatabase implements UserDao {

  ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

  @Override
  public void save(User user) {
    String id = UUID.randomUUID().toString();

    users.put(id, user);
    System.out.println("User Created: " + users);
  }

  @Override
  public Optional<User> findById(String id) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return Optional.of(users.get(email));
  }
}

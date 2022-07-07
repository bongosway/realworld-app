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

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

  @Id
  protected String id;

  @Column(unique = true)
  private String username;
  private String email;
  private String token;
  private String password;
  private String bio;
  private String image;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

  protected User() {
  }

  User(String id, String username, String email, String password, String bio, String image, String token) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.token = token;
    this.bio = bio;
    this.image = image;
    this.createdOn = LocalDateTime.now();
  }

  User(String username, String email, String password) {
    this(UUID.randomUUID().toString(), username, email, password, "", "", "fakeToken");
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getBio() {
    return bio;
  }

  public String getToken() {
    return token;
  }

  public String getImage() {
    return image;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setImage(String image) {
    this.image = image;
  }

  protected void setUsername(String username) {
    this.username = username;
  }

  protected void setEmail(String email) {
    this.email = email;
  }

  protected void setPassword(String password) {
    this.password = password;
  }

  protected void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", token='" + token + '\'' +
        ", bio='" + bio + '\'' +
        ", image='" + image + '\'' +
        '}';
  }
}

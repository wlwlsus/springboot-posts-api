package com.cad.book.springboot.web.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * @Getter : 선언된 모든 필드에 get 메소드 생성
 * @RequiredArgsConstructor : 선언된 모든 final 필드가 포함된 생성자를 생성해준다.
 * final 이 없는 필드는 생성자에 포함 X
 */
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

  private final String name;
  private final int amount;
}

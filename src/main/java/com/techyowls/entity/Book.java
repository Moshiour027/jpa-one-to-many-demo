package com.techyowls.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book  extends BaseEntity{
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "isbn", nullable = false)
	private String isbn;

	@Column(name = "page_count", nullable = false)
	private Integer pageCount;

	@Column(name = "published_date", nullable = false)
	private LocalDate publishedDate;
}

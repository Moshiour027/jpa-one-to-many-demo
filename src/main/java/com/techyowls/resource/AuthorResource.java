package com.techyowls.resource;

import com.techyowls.entity.Author;
import com.techyowls.exception.ResourceNotFoundException;
import com.techyowls.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.techyowls.resource.ResponseUtil.resourceUri;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorResource {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(
            @Valid @RequestBody Author request
    ) {
        return Optional.of(request)
                .map(authorRepository::save)
                .map(
                        author -> ResponseEntity
                                .created(resourceUri(author.getId()))
                                .body(author)
                )
                .orElseThrow(IllegalArgumentException::new);
    }

    @PutMapping("/authors/{authorId}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable final Long authorId,
            @Valid @RequestBody Author request
    ) {
        return authorRepository.findById(authorId)
                .map(
                        author -> {
                            author.setAge(request.getAge());
                            author.setBirthDate(request.getBirthDate());
                            author.setName(request.getName());
                            return author;
                        }
                )
                .map(authorRepository::save)
                .map(author -> ResponseEntity
                        .ok()
                        .location(resourceUri(authorId))
                        .body(author))
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "AuthorID " + authorId + " not found"
                        )
                );
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<?> deleteAuthor(
            @PathVariable Long authorId
    ) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    authorRepository.delete(author);
                    return ResponseEntity
                            .ok()
                            .build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                                "AuthorID " + authorId + " not found"
                        )
                );
    }
}

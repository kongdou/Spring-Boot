package fun.deepsky.springbootinaction.securedwebtest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book,Long>{

	List<Book> findByReader(Reader reader);
}

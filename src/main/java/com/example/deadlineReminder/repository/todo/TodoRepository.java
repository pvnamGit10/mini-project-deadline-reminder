package com.example.deadlineReminder.repository.todo;

import com.example.deadlineReminder.entity.todo.Todo;
import com.example.deadlineReminder.entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Modifying
    @Query("update Todo t set t.title = ?1, t.des = ?2, t.beginOn =?3, t.endOn = ?4, t.isDone = false , t.inProcess = false , t.inReady = false where t.id =?5")
    void updateTodo(String title, String des, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginOn,
                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endOn,Long id);


    @Modifying
    @Query("update Todo t set t.isDone = true ,t.inReady =false , t.inProcess=false where t.id = ?1")
    void makeComplete(Long id);

    @Modifying
    @Query("update Todo t set t.isDone =?1, t.inProcess = ?2, t.inReady = ?3 where t.id = ?4")
    void checkStatus(Boolean isDone, Boolean inProcess, Boolean inReady, Long id);

    @Query("select t from Todo t where t.userAccount = ?1")
    List<Todo> getListTodo(UserAccount account);

    @Query("select t from Todo t where t.inReady = true and t.userAccount = ?1")
    List<Todo> getListInReady(UserAccount account);

    @Query("select t from Todo t where t.inProcess = true and t.userAccount = ?1")
    List<Todo> getListInProcess(UserAccount account);

    @Query("select t from Todo t where t.isDone = true and t.userAccount = ?1")
    List<Todo> getLisDone(UserAccount account);

    @Query("select t from Todo t where (t.title like %?1% or t.des like %?1%) and t.userAccount = ?2")
    List<Todo> getListSearch(String searchValue, UserAccount account);

    @Query("select t.isDone from Todo t where t.isDone = true and t.id =?1")
    Optional<Todo> isDone(Long id);
}

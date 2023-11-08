package pl.edu.pg.eti.kask.rpg.execution.entity;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.rpg.creature.entity.Creature;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Execution extends Creature {

    private UUID id;
    private User user;
    // private Habit habit;
    private LocalDate date;
    private boolean isDone;
    private String userComment;

}

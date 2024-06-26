package pl.edu.pg.eti.kask.rpg.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.entity.Skill;
import pl.edu.pg.eti.kask.rpg.character.service.CharacterService;
import pl.edu.pg.eti.kask.rpg.character.service.ProfessionService;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;
import pl.edu.pg.eti.kask.rpg.execution.service.ExecutionService;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.entity.UserRoles;
import pl.edu.pg.eti.kask.rpg.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

/**
 * Listener started automatically on servlet context initialized. Fetches
 * instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch
 * database datasource and init data only in
 * cases of empty database. When using persistence storage application instance
 * should be initialized only during first
 * run in order to init database with starting data. Good place to create first
 * default admin user.
 */
@ApplicationScoped
public class InitializedData {

        private final CharacterService characterService;

        private final ProfessionService professionService;

        private final UserService userService;

        private final ExecutionService executionService;

        private final RequestContextController requestContextController;

        /**
         * @param characterService         character service
         * @param userService              user service
         * @param professionService        profession service
         * @param requestContextController CDI request context controller
         */
        @Inject
        public InitializedData(
                        CharacterService characterService,
                        UserService userService,
                        ProfessionService professionService,
                        ExecutionService executionService,
                        RequestContextController requestContextController) {
                this.characterService = characterService;
                this.userService = userService;
                this.professionService = professionService;
                this.executionService = executionService;
                this.requestContextController = requestContextController;
        }

        public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
                init();
        }

        /**
         * Initializes database with some example values. Should be called after
         * creating this object. This object should be
         * created only once.
         */
        @SneakyThrows
        private void init() {
                requestContextController.activate();

                User admin = User.builder()
                                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                                .login("admin")
                                .email("admin@example.com")
                                .password("adminadmin")
                                .portrait(getResourceAsByteArray("../avatar/calvian.png"))// package relative path
                                .build();

                User kevin = User.builder()
                                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                                .login("kevin")
                                .email("kevin@example.com")
                                .password("useruser")
                                .portrait(getResourceAsByteArray("../avatar/eloise.png"))// package relative path
                                .build();

                User alice = User.builder()
                                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                                .login("alice")
                                .email("alice@example.com")
                                .password("useruser")
                                .portrait(getResourceAsByteArray("../avatar/zereni.png"))// package relative path
                                .build();

                userService.create(admin);
                userService.create(kevin);
                userService.create(alice);

                Execution monday = Execution.builder()
                                .id(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"))
                                .user(admin)
                                .date(LocalDate.of(2020, Month.JANUARY, 8))
                                .isDone(false)
                                .userComment("lorem1")
                                .build();

                Execution tuesday = Execution.builder()
                                .id(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3455"))
                                .user(alice)
                                .date(LocalDate.of(2020, Month.JANUARY, 9))
                                .isDone(true)
                                .userComment("lorem1")
                                .build();

                Execution wednesday = Execution.builder()
                                .id(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3456"))
                                .user(alice)
                                .date(LocalDate.of(2020, Month.JANUARY, 10))
                                .isDone(false)
                                .userComment("lorem1")
                                .build();

                executionService.create(monday);
                executionService.create(tuesday);
                executionService.create(wednesday);
                

                Skill attack = Skill.builder()
                                .name("Attack")
                                .description("Attacks an enemy with light attack.")
                                .build();

                Skill backStab = Skill.builder()
                                .name("Back stab")
                                .description("Attacks an enemy from behind with medium attack.")
                                .build();

                Skill heal = Skill.builder()
                                .name("Heal self")
                                .description("Heals self.")
                                .build();

                Skill charm = Skill.builder()
                                .name("Charm creature")
                                .description("Charm creature and convinces to run away.")
                                .build();

                Skill heavyAttack = Skill.builder()
                                .name("Heavy attack")
                                .description("Attacks an enemy with heavy attack.")
                                .build();

                Profession bard = Profession.builder()
                                .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                                .name("Bard")
                                .skill(1, attack)
                                .skill(2, charm)
                                .build();

                Profession cleric = Profession.builder()
                                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                                .name("Cleric")
                                .skill(1, attack)
                                .skill(2, heal)
                                .build();

                Profession warrior = Profession.builder()
                                .id(UUID.fromString("2d9b1e8c-67c5-4188-a911-5f064a63d8cd"))
                                .name("Warrior")
                                .skill(1, attack)
                                .skill(2, heavyAttack)
                                .build();

                Profession rogue = Profession.builder()
                                .id(UUID.randomUUID())
                                .name("Rogue")
                                .skill(1, attack)
                                .skill(2, backStab)
                                .build();

                professionService.create(bard);
                professionService.create(cleric);
                professionService.create(warrior);
                professionService.create(rogue);

                Character calvian = Character.builder()
                                .id(UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0"))
                                .name("Calvian")
                                .age(18)
                                .background("A young bard with some infernal roots.")
                                .experience(0)
                                .level(1)
                                .profession(bard)
                                .charisma(16)
                                .constitution(12)
                                .strength(8)
                                .health(2 * 12)
                                .portrait(getResourceAsByteArray("../avatar/calvian.png"))// package relative path
                                .user(kevin)
                                .build();

                Character uhlbrecht = Character.builder()
                                .id(UUID.fromString("cc0b0577-bb6f-45b7-81d6-3db88e6ac19f"))
                                .name("Uhlbrecht")
                                .age(37)
                                .background("Quite experienced half-orc warrior.")
                                .experience(0)
                                .level(1)
                                .profession(warrior)
                                .charisma(8)
                                .constitution(10)
                                .strength(18)
                                .health(2 * 10)
                                .portrait(getResourceAsByteArray("../avatar/uhlbrecht.png"))// package relative path
                                .user(kevin)
                                .build();

                Character eloise = Character.builder()
                                .id(UUID.fromString("f08ef7e3-7f2a-4378-b1fb-2922d730c70d"))
                                .name("Eloise")
                                .age(32)
                                .background("Human cleric.")
                                .experience(0)
                                .level(1)
                                .profession(cleric)
                                .charisma(8)
                                .constitution(12)
                                .strength(14)
                                .health(2 * 12)
                                .portrait(getResourceAsByteArray("../avatar/eloise.png"))// package relative path
                                .user(alice)
                                .build();

                Character zereni = Character.builder()
                                .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                                .name("Zereni")
                                .age(20)
                                .background("Half elf rogue.")
                                .experience(0)
                                .level(1)
                                .profession(rogue)
                                .charisma(14)
                                .constitution(12)
                                .strength(10)
                                .health(2 * 12)
                                .portrait(getResourceAsByteArray("../avatar/zereni.png"))// package relative path
                                .user(alice)
                                .build();

                characterService.create(calvian);
                characterService.create(uhlbrecht);
                characterService.create(eloise);
                characterService.create(zereni);

                requestContextController.deactivate();
        }

        /**
         * @param name name of the desired resource
         * @return array of bytes read from the resource
         */
        @SneakyThrows
        private byte[] getResourceAsByteArray(String name) {
                try (InputStream is = this.getClass().getResourceAsStream(name)) {
                        if (is != null) {
                                return is.readAllBytes();
                        } else {
                                throw new IllegalStateException("Unable to get resource %s".formatted(name));
                        }
                }
        }

}

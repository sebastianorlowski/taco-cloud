package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "pszenna", Ingredient.Type.WRAP));
                repo.save(new Ingredient("COTO", "kukurydziana", Ingredient.Type.WRAP));
                repo.save(new Ingredient("GRBF", "mielona wołowina", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("CARN", "kawałki mięsa", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("TMTO", "pomidory pokrojone w kostkę", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("LETC", "sałata", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("CHED", "cheddar", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("SLSA", "pikantny sos pomidorowy", Ingredient.Type.SAUCE));
                repo.save(new Ingredient("SRCR", "śmietana", Ingredient.Type.SAUCE));
            }
        };
    }
}

package com.starbank.recommendation.ProductRecommender.service;

import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import com.starbank.recommendation.ProductRecommender.model.Rule;
import com.starbank.recommendation.ProductRecommender.model.User;
import com.starbank.recommendation.ProductRecommender.repository.ProductRepository;
import com.starbank.recommendation.ProductRecommender.repository.RuleRepository;
import com.starbank.recommendation.ProductRecommender.rules.RecommendationRules;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ProductRepository productRepository;
    private final List<Recommendation> recommendations;
    private final RuleRepository ruleRepository;

    public RecommendationService(ProductRepository productRepository, List<Recommendation> recommendations, RuleRepository ruleRepository) {
        this.productRepository = productRepository;
        this.recommendations = AllRecommendations();
        this.ruleRepository = ruleRepository;
    }

    //Добавление правила
    public Rule addRule(Rule rule){
        return ruleRepository.save(rule);
    }

    //Вывод всех правил
    public List<Rule> getAllRules(){
        return ruleRepository.findAll();
    }

    //Удаление правила
    public void deleteRule(UUID id){
        ruleRepository.deleteById(id);
    }

    //Создаем лист со всеми правилами
    private List<Recommendation> AllRecommendations() {
        List<Recommendation> recommendationList = new ArrayList<>();

        //Добавляем правила в лист при инициализации
        Recommendation invest500 = new Recommendation();
        invest500.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        invest500.setName("Invest 500");
        invest500.setText("Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
        invest500.setSqlRules(RecommendationRules.INVEST_500_RULE);
        recommendationList.add(invest500);

        Recommendation topSaving = new Recommendation();
        topSaving.setId(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"));
        topSaving.setName("Top Saving");
        topSaving.setText("Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
                "Преимущества «Копилки»:\n" +
                "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
                "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
                "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
                "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
        topSaving.setSqlRules(RecommendationRules.TOP_SAVING_RULE);
        recommendationList.add(topSaving);

        Recommendation simpleCredit = new Recommendation();
        simpleCredit.setId(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
        simpleCredit.setName("Простой кредит");
        simpleCredit.setText("Откройте мир выгодных кредитов с нами!\n" +
                "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
                "Почему выбирают нас:\n" +
                "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
                "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
                "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");
        simpleCredit.setSqlRules(RecommendationRules.SIMPLE_CREDIT_RULE);
        recommendationList.add(simpleCredit);

        return recommendationList;
    }

    //Отправляем пользователям рекомендации
    @Cacheable(cacheNames = "recommendations", key = "#userId")
    public List<Recommendation> getRecommendations(UUID userId) {
        User user = new User();
        user.setId(userId);

        //Проверяем соответствие по методу репозитория
        return recommendations.stream()
                .filter(recommendation -> productRepository.isRecommendation(recommendation, user))
                .collect(Collectors.toList());
    }
}

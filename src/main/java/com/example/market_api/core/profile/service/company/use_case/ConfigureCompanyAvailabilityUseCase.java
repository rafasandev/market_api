package com.example.market_api.core.profile.service.company.use_case;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.common.exception.BusinessRuleException;
import com.example.market_api.core.profile.dto.company.CompanyAvailabilityForm;
import com.example.market_api.core.profile.dto.company.CompanyDailyAvailabilityForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.market_api.core.profile.dto.company.TimeRangeForm;
import com.example.market_api.core.profile.mapper.ProfileMapper;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.model.company.value.CompanyDailyAvailability;
import com.example.market_api.core.profile.service.company.CompanyProfileService;
import com.example.market_api.core.user.model.User;
import com.example.market_api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigureCompanyAvailabilityUseCase {

    private final CompanyProfileService companyProfileService;
    private final UserService userService;
    private final ProfileMapper profileMapper;

    @Transactional
    public CompanyProfileResponseDto configureAvailability(UUID companyId, CompanyAvailabilityForm form) {
        CompanyProfile company = companyProfileService.getById(companyId);
        User loggedUser = userService.getLoggedInUser();
        validateCompanyOwnership(company, loggedUser);

        // Normaliza e valida os dias da semana disponíveis
        List<Integer> validatedWeekDays = validateAvailabilityDays(form.getWeekDaysAvailable());

        // Constrói e valida os horários disponíveis, criando uma lista com os dias da
        // semana e os horários associados
        List<CompanyDailyAvailability> availabilitySlots = buildAvailabilityInfoByDay(
                form.getDailyAvailability(),
                validatedWeekDays);

        company.setWeekDaysAvailable(validatedWeekDays);
        company.setDailyAvailableTimeRanges(availabilitySlots);
        companyProfileService.validateCompanyCanBeActivated(company);
        if (loggedUser.userHasContactInfoFilled())
            loggedUser.setStatus(true);

        CompanyProfile saved = companyProfileService.save(company);
        return profileMapper.toResponseDto(saved);
    }

    private void validateCompanyOwnership(CompanyProfile company, User loggedUser) {
        if (company == null || loggedUser == null || !company.getId().equals(loggedUser.getId())) {
            throw new BusinessRuleException("Usuário logado não pode alterar a disponibilidade desta empresa");
        }
    }

    private List<Integer> validateAvailabilityDays(List<Integer> weekDaysWithAvailability) {
        if (weekDaysWithAvailability == null || weekDaysWithAvailability.isEmpty()) {
            throw new BusinessRuleException("Informe pelo menos um dia da semana disponível");
        }
        return weekDaysWithAvailability.stream()
                .map(day -> {
                    if (day == null || day < 1 || day > 7)
                        throw new BusinessRuleException("Os dias da semana devem estar entre 1 (domingo) e 7 (sábado)");
                    return day;
                })
                .collect(Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), ArrayList::new));
    }

    private List<CompanyDailyAvailability> buildAvailabilityInfoByDay(
            List<CompanyDailyAvailabilityForm> dailyAvailabilityForms,
            List<Integer> availabilityDays) {

        if (dailyAvailabilityForms == null || dailyAvailabilityForms.isEmpty()) {
            throw new BusinessRuleException("Informe os horários disponíveis para os dias selecionados");
        }

        // Cria um hash map para os dias permitidos
        Set<Integer> availabilityDaysHash = new LinkedHashSet<>(availabilityDays);

        // Agrupa os formulários por dia da semana
        Map<Integer, List<CompanyDailyAvailabilityForm>> availabilityFormGroupByWeekday = dailyAvailabilityForms
                .stream()
                .collect(Collectors.groupingBy(CompanyDailyAvailabilityForm::getWeekDay));

        // Verificação se todos os dias permitidos possuem horários configurados
        if (!availabilityDaysHash.equals(availabilityFormGroupByWeekday.keySet())) {
            throw new BusinessRuleException("Todos os dias informados devem possuir ao menos um intervalo configurado");
        }

        List<CompanyDailyAvailability> dailyAvailabilitySlots = new ArrayList<>();

        for (Entry<Integer, List<CompanyDailyAvailabilityForm>> entry : availabilityFormGroupByWeekday.entrySet()) {
            Integer day = entry.getKey();
            if (!availabilityDaysHash.contains(day)) {
                throw new BusinessRuleException("Dia da semana " + day + " não está na lista de dias habilitados");
            }
            // Construção dos horários para o dia atual
            List<CompanyDailyAvailability> daySlots = entry
                    .getValue()
                    .stream()
                    .flatMap(form -> form
                            .getTimeRanges()
                            .stream()
                            .map(range -> createSlot(day, range)))
                    .sorted(Comparator.comparing(CompanyDailyAvailability::getStartTime))
                    .toList();

            validateDaySlots(day, daySlots);

            // Adiciona os horários validados à lista principal
            dailyAvailabilitySlots.addAll(daySlots);
        }

        return dailyAvailabilitySlots;
    }

    // Método auxiliar para criar um horário disponível a partir do formulário
    private CompanyDailyAvailability createSlot(Integer day, TimeRangeForm rangeForm) {
        LocalTime startTime = rangeForm.getStartTime();
        LocalTime endTime = rangeForm.getEndTime();

        if (startTime == null || endTime == null) {
            throw new BusinessRuleException("Horários inicial e final devem ser informados para todos os intervalos");
        }

        if (!startTime.isBefore(endTime)) {
            throw new BusinessRuleException("O horário inicial deve ser menor que o horário final em cada intervalo");
        }

        return CompanyDailyAvailability.builder()
                .weekDay(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    private void validateDaySlots(Integer day, List<CompanyDailyAvailability> dailySlots) {
        if (dailySlots == null || dailySlots.isEmpty()) {
            throw new BusinessRuleException("Informe ao menos um horário disponível para o dia " + day);
        }

        CompanyDailyAvailability previous = null;
        
        for (CompanyDailyAvailability current : dailySlots) {
            if (previous != null && !current.getStartTime().isAfter(previous.getEndTime())) {
                throw new BusinessRuleException("Os intervalos do dia " + day + " não podem se sobrepor");
            }
            previous = current;
        }
    }
}

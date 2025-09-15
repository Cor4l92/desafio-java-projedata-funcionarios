package main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        // Formato para exibir datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Lista de funcionários
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 – Inserir todos os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 10), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 3), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1562.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3011.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2789.93"), "Gerente"));

        // 3.2 – Remover o funcionário “João”
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 – Imprimir todos os funcionários com formatação
        System.out.println("\n=== Todos os Funcionários ===");
        for (Funcionario f : funcionarios) {
            String dataFormatada = f.getDataNascimento().format(formatter);
            String salarioFormatado = String.format("%.2f", f.getSalario().doubleValue()).replace('.', ',');
            System.out.printf("Nome: %s | Data Nasc.: %s | Salário: R$ %s | Função: %s%n",
                    f.getNome(), dataFormatada, salarioFormatado, f.getFuncao());
        }

        // 3.4 – Aumento de 10% no salário
        System.out.println("\n=== Após aumento de 10% ===");
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario);
        }

        // 3.5 – Agrupar por função em Map
        Map<String, List<Funcionario>> mapaPorFuncao = new HashMap<>();
        for (Funcionario f : funcionarios) {
            mapaPorFuncao.computeIfAbsent(f.getFuncao(), k -> new ArrayList<>()).add(f);
        }

        // 3.6 – Imprimir agrupados por função
        System.out.println("\n=== Funcionários por Função ===");
        for (Map.Entry<String, List<Funcionario>> entry : mapaPorFuncao.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            for (Funcionario f : entry.getValue()) {
                String dataFormatada = f.getDataNascimento().format(formatter);
                String salarioFormatado = String.format("%.2f", f.getSalario().doubleValue()).replace('.', ',');
                System.out.printf("  - %s | %s | R$ %s%n", f.getNome(), dataFormatada, salarioFormatado);
            }
        }

        // 3.8 – Funcionários que fazem aniversário em outubro (10) e dezembro (12)
        System.out.println("\n=== Aniversariantes em Outubro e Dezembro ===");
        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                String dataFormatada = f.getDataNascimento().format(formatter);
                String salarioFormatado = String.format("%.2f", f.getSalario().doubleValue()).replace('.', ',');
                System.out.printf("Nome: %s | Data: %s | Salário: R$ %s%n",
                        f.getNome(), dataFormatada, salarioFormatado);
            }
        }

        // 3.9 – Funcionário com maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Pessoa::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = java.time.Year.now().getValue() - maisVelho.getDataNascimento().getYear();
            System.out.println("\n=== Funcionário mais velho ===");
            System.out.printf("Nome: %s | Idade: %d anos%n", maisVelho.getNome(), idade);
        }

        // 3.10 – Ordenar por ordem alfabética
        System.out.println("\n=== Funcionários em ordem alfabética ===");
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        for (Funcionario f : funcionarios) {
            String dataFormatada = f.getDataNascimento().format(formatter);
            String salarioFormatado = String.format("%.2f", f.getSalario().doubleValue()).replace('.', ',');
            System.out.printf("%s | %s | R$ %s | %s%n",
                    f.getNome(), dataFormatada, salarioFormatado, f.getFuncao());
        }

        // 3.11 – Total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\n=== Total dos salários: R$ %.2f%n", totalSalarios.doubleValue());

        // 3.12 – Quantos salários mínimos cada um ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n=== Quantos salários mínimos cada funcionário ganha ===");
        for (Funcionario f : funcionarios) {
            double qtdSalariosMinimos = f.getSalario().doubleValue() / salarioMinimo.doubleValue();
            System.out.printf("%s: %.2f salários mínimos%n", f.getNome(), qtdSalariosMinimos);
        }
    }
}
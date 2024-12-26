# Credit Card API

Este projeto é uma API desenvolvida em Java 17 utilizando Spring Boot, que realiza a validação de números de cartões de crédito e identifica a bandeira do cartão. A API aceita números de cartões que podem conter espaços ou outros separadores, que são descartados durante o processamento.

## Estrutura do Projeto

```shell
credit-card-api
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── CreditCardApiApplication.java
│   │   │           ├── controller
│   │   │           │   └── CreditCardController.java
│   │   │           ├── service
│   │   │           │   └── CreditCardService.java
│   │   │           └── util
│   │   │               └── CreditCardValidator.java
│   │   └── resources
│   │       └── application.properties
│   ├── test
│       ├── java
│       │   └── com
│       │       └── example
│       │           ├── controller
│       │           │   └── CreditCardControllerTest.java
│       │           ├── service
│       │           │   └── CreditCardServiceTest.java
│       │           └── util
│       │               └── CreditCardValidatorTest.java
├── build.gradle
└── README.md
```

## Instruções de Configuração

1. **Clone o repositório**:

```shell
git clone <repository-url>
cd credit-card-api
```

2.**Empacote o projeto**:

```shell
./gradle wrapper
```

3.**Construa o projeto**:

```shell
./gradlew build
```

4.**Execute a aplicação**:

```shell
./gradlew bootRun
```

## Uso

Para validar um número de cartão de crédito, envie uma requisição POST para o endpoint `/validate` com o número do cartão no corpo da requisição.

### Exemplo de Requisição

```shell
POST /validate
Content-Type: application/json

{
  "cardNumber": "4111 1111 1111 1111"
}
```

### Exemplo de Resposta

```json
{
  "valid": true,
  "brand": "Visa"
}
```

## Testes

Os testes unitários estão localizados na pasta `src/test/java/com/example`. Eles cobrem os controladores, serviços e utilitários da aplicação, garantindo que a lógica de validação e identificação de bandeiras funcione corretamente.

## Dependências

Este projeto utiliza as seguintes dependências:

- Spring Boot
- JUnit
- Mockito

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou relatar problemas.

## Regras para validação de cartão de crédito

### Detalhes das Bandeiras

| BANDEIRA                   | NÚMERO DE DÍGITOS | DÍGITOS DO CVV |
|----------------------------|-------------------|----------------|
| Visa                       | 13 ou 16 dígitos  | 3 dígitos      |
| Mastercard                 | 16 dígitos        | 3 dígitos      |
| Amex                       | 15 dígitos        | 4 dígitos      |
| Diners Club International  | 14 dígitos        | 3 dígitos      |
| JCB                        | 16 dígitos        | 3 dígitos      |
| ELO                        | 16 dígitos        | 3 dígitos      |
| Enroute                    | 15 dígitos        | 3 digitos      |
| Aura                       | 16 dígitos        | 3 digitos      |

### **Algoritmo de Luhn**

O número do cartão deve passar na verificação do algoritmo de Luhn para ser considerado válido.

## Identificação de Cartão Mastercard

Para identificar se um cartão de crédito é Mastercard pelo seu número, você deve verificar se o número do cartão começa com um dos seguintes prefixos: `51`, `52`, `53`, `54`, `55` ou qualquer número no intervalo de `2221` a `2720`.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern MASTERCARD_PATTERN = Pattern.compile("^(5[1-5][0-9]{14}|2(2[2-9][0-9]{12}|[3-6][0-9]{13}|7[01][0-9]{12}|720[0-9]{12}))$");

  public static boolean isMasterCard(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return MASTERCARD_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "5234 5678 9012 3456";
    System.out.println("Is Mastercard: " + isMasterCard(cardNumber));
  }
}
```

## Validação de Cartão American Express

Para validar se um número de cartão de crédito pertence à American Express, você deve verificar se o número do cartão começa com os prefixos `34` ou `37` e possui 15 dígitos. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern AMEX_PATTERN = Pattern.compile("^3[47][0-9]{13}$");

  public static boolean isAmericanExpress(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return sanitizedNumber.length() <= 19 && AMEX_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "3714 496353 98431";
    System.out.println("Is American Express: " + isAmericanExpress(cardNumber));
  }
}
```

## Validação de Cartão Diners Club

Para validar se um número de cartão de crédito pertence ao Diners Club, você deve verificar se o número do cartão possui 14 dígitos e começa com os prefixos `36`, `38`, `39`, `300` a `305`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern DINERS_CLUB_PATTERN = Pattern.compile("^(36|38|39|30[0-5])[0-9]{11,12}$");

  public static boolean isDinersClub(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return DINERS_CLUB_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "3852 0000 0232 37";
    System.out.println("Is Diners Club: " + isDinersClub(cardNumber));
  }
}
```

## Validação de Cartão Discover

Para validar se um número de cartão de crédito pertence ao Discover, você deve verificar se o número do cartão começa com os prefixos `6011`, `622126-622925`, `644-649`, ou `65` e possui 16 dígitos. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern DISCOVER_PATTERN = Pattern.compile("^(6011\\d{12}|622(12[6-9]|1[3-9]\\d|[2-8]\\d{2}|9[01]\\d|92[0-5])\\d{10}|64[4-9]\\d{13}|65\\d{14})$");

  public static boolean isDiscover(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return DISCOVER_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "6011 1234 5678 9012";
    System.out.println("Is Discover: " + isDiscover(cardNumber));
  }
}
```

## Validação de Cartão JCB

Para validar se um número de cartão de crédito pertence ao JCB, você deve verificar se o número do cartão começa com os prefixos `3528` a `3589` e possui 16 dígitos. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern JCB_PATTERN = Pattern.compile("^(352[89]|35[3-8]\\d)\\d{12}$");

  public static boolean isJCB(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return JCB_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "3530 1234 5678 9012";
    System.out.println("Is JCB: " + isJCB(cardNumber));
  }
}
```

## Validação de Cartão Enroute

Para validar se um número de cartão de crédito pertence ao Enroute, você deve verificar se o número do cartão possui 15 dígitos e começa com os prefixos `2014` ou `2149`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern ENROUTE_PATTERN = Pattern.compile("^(2014|2149)\\d{11}$");

  public static boolean isEnroute(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return ENROUTE_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "2014 1234 5678 901";
    System.out.println("Is Enroute: " + isEnroute(cardNumber));
  }
}
```

## Validação de Cartão Aura

Para validar se um número de cartão de crédito pertence ao Aura, você deve verificar se o número do cartão possui 16 dígitos e começa com o prefixo `50`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern AURA_PATTERN = Pattern.compile("^50\\d{14}$");

  public static boolean isAura(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return AURA_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "5012 3456 7890 1234";
    System.out.println("Is Aura: " + isAura(cardNumber));
  }
}
```

## Validação de Cartão Voyager

Para validar se um número de cartão de crédito pertence ao Voyager, você deve verificar se o número do cartão possui 15 dígitos e começa com o prefixo `8699`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern VOYAGER_PATTERN = Pattern.compile("^8699\\d{11}$");

  public static boolean isVoyager(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return VOYAGER_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "8699 1234 5678 901";
    System.out.println("Is Voyager: " + isVoyager(cardNumber));
  }
}
```

## Validação de Cartão Hipercard

Para validar se um número de cartão de crédito pertence ao Hipercard, você deve verificar se o número do cartão possui 13, 16 ou 19 dígitos e começa com o prefixo `606282` ou `3841`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern HIPERCARD_PATTERN = Pattern.compile("^(606282|3841)\\d{10,17}$");

  public static boolean isHipercard(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return HIPERCARD_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "6062 8234 5678 9012";
    System.out.println("Is Hipercard: " + isHipercard(cardNumber));
  }
}
```

## Validação de Cartão Visa

Para validar se um número de cartão de crédito pertence ao Visa, você deve verificar se o número do cartão começa com o prefixo `4` e possui 13 ou 16 dígitos. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern VISA_PATTERN = Pattern.compile("^4\\d{12}(\\d{3})?$");

  public static boolean isVisa(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return VISA_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "4111 1111 1111 1111";
    System.out.println("Is Visa: " + isVisa(cardNumber));
  }
}
```

## Validação de Cartão ELO

Para validar se um número de cartão de crédito pertence ao ELO, você deve verificar se o número do cartão possui 16 dígitos e começa com um dos seguintes prefixos: `4011`, `4312`, `4389`, `4514`, `4576`, `5041`, `5066`, `5090`, `6277`, `6362`, `6363`, `6500`, `6504`, `6505`, `6516`, `6550`. Abaixo está uma função em Java que utiliza regex para realizar essa validação.

```java
import java.util.regex.Pattern;

public class CreditCardValidator {

  private static final Pattern ELO_PATTERN = Pattern.compile("^(4011|4312|4389|4514|4576|5041|5066|5090|6277|6362|6363|6500|6504|6505|6516|6550)\\d{12}$");
 
  public static boolean isElo(String cardNumber) {
    String sanitizedNumber = cardNumber.replaceAll("\\s+", "");
    return ELO_PATTERN.matcher(sanitizedNumber).matches();
  }

  public static void main(String[] args) {
    String cardNumber = "5066 9911 1111 1118";
    System.out.println("Is ELO: " + isElo(cardNumber));
  }
}
```

> :satellite: código foi gerado por IA - GitHub Copilot, e revisado por interação humana,
> :satellite: utilizado o github inline e github chat, os comandos de prompt, além de terem sido digitados conforme o contexto,
> :satellite: utilizado a extensão VS Code Speech ***(ms-vscode.vscode-speech)*** para geração por voz*
> by Dannyrooh F. Campos

package com.userleonardolopez.conversordemonedas.principal;

import com.userleonardolopez.conversordemonedas.consultas.ConsultaApi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    private static final String OPCION_INVALIDA = "Opción inválida";

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean cerrarPrograma = false;

        System.out.println("""
                ¡Bienvenido a CoinExchanger!
                Este es un conversor de divisas que actualiza sus
                resultados en tiempo real.
                
                *#*# Seleccione una opción: #*#*
                1. Convertir alguna divisa
                2. Cerrar programa
                """);
        int opcionUsuario = validarOpcionInt(scan);

        while (!cerrarPrograma) {
            switch (opcionUsuario) {
                case 1:
                    while (!cerrarPrograma) {
                        ejecutarConversor();

                        System.out.println("\n¿Desea realizar otra conversión?\n\n1. Sí\n2. No");
                        opcionUsuario = validarOpcionInt(scan);

                        while (opcionUsuario < 1 || opcionUsuario > 2) {
                            System.out.println(OPCION_INVALIDA);
                            opcionUsuario = validarOpcionInt(scan);
                        }
                        if (opcionUsuario == 2) {
                            cerrarPrograma = true;
                        }
                    }
                    System.out.println("¡Hasta luego!");
                    break;
                case 2:
                    System.out.println("¡Hasta luego!");
                    cerrarPrograma = true;
                    break;
                default:
                    System.out.println(OPCION_INVALIDA);
                    opcionUsuario = validarOpcionInt(scan);
            }
        }
    }

    public static void ejecutarConversor() throws IOException {
        Scanner scan = new Scanner(System.in);
        var opcionesUsuario = new ArrayList<Integer>();
        String[] monedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD"};
        String listaDeMonedas = """
                1. ARS - Peso argentino
                2. BOB - Boliviano boliviano
                3. BRL - Real brasileño
                4. CLP - Peso chileno
                5. COP - Peso colombiano
                6. USD - Dólar estadounidense
                """;


        int contador = 0;
        int opcionUsuario = 0;
        //Saldrá hasta que se hayan brindado 2 opciones correctas (monedas para procesar)
        while (contador < 2) {

            if (contador == 0) {
                System.out.println("¿Qué moneda desea convertir?\n\n" + listaDeMonedas);
                opcionUsuario = validarOpcionInt(scan);
            } else if (contador == 1) {
                System.out.println("¿A cuál moneda lo desea convertir?");
                opcionUsuario = validarOpcionInt(scan);
            }

            if (opcionUsuario >= 1 && opcionUsuario <= monedas.length) {
                opcionesUsuario.add(opcionUsuario - 1);
                contador++;
            } else {
                System.out.println(OPCION_INVALIDA);
            }
        }

        System.out.println("¿De cuánto es el monto?");
        BigDecimal monto = new BigDecimal(String.valueOf(validarMontoBigDecimal(scan)));

        var apiExchange = new ConsultaApi(monedas[opcionesUsuario.get(0)], monedas[opcionesUsuario.get(1)], monto);
        System.out.println(apiExchange.getRespuesta());
    }

    public static int validarOpcionInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Escriba el número de la opción que desee");
            scan.nextLine();
        }
        return scan.nextInt();
    }

    public static BigDecimal validarMontoBigDecimal(Scanner scan) {
        while (!scan.hasNextBigDecimal()) {
            System.out.println("Valor inválido. Por favor, ingrese un monto");
            scan.nextLine();
        }
        return scan.nextBigDecimal();
    }
}


package calculadora;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class calculadora {

    private JFrame frame;
    private JTextField JTFResultado;
    private double valor1 = 0;
    private String op = "";
    private boolean novoNumero = true;

    // Informações do aluno
    private final String NOME = "Luiz felipe gouvea martins";
    private final String RA = "0024807";

    // Cores baseadas nos números da data de nascimento
    // Números informados: 131, 103 e 207
    // O valor 207 foi utilizado como base do vermelho
    // Os demais componentes foram ajustados para obter o tom vermelho

    private final Color VERMELHO = new Color(207, 49, 55);
    private final Color VERMELHO_CLARO = new Color(245, 210, 215);
    private final Color FUNDO = new Color(253, 239, 240);
    private final Color TEXTO = new Color(75, 33, 39);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    calculadora window = new calculadora();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public calculadora() {
        initialize();
    }

    /** Cria um botão na posição indicada e já adiciona na tela. */
    private JButton criarBotao(String texto, int x, int y, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBounds(x, y, 54, 53);

        // Cores dos botões
        botao.setBackground(Color.WHITE);
        botao.setForeground(TEXTO);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(BorderFactory.createLineBorder(VERMELHO_CLARO));

        if (texto.equals("+") || texto.equals("-") ||
            texto.equals("*") || texto.equals("/") ||
            texto.equals("^")) {

            botao.setBackground(VERMELHO_CLARO);
            botao.setForeground(VERMELHO);
        }

        if (texto.equals("=")) {
            botao.setBackground(VERMELHO);
            botao.setForeground(Color.WHITE);
        }

        if (texto.equals("C")) {
            botao.setBackground(new Color(255, 225, 230));
            botao.setForeground(new Color(160, 50, 70));
        }

        frame.getContentPane().add(botao);
        return botao;
    }

    /** Botões numéricos: acrescenta o dígito ao que já está no visor. */
    private void digitar(String d) {
        if (novoNumero) {
            JTFResultado.setText(d);
            novoNumero = false;
        } else {
            JTFResultado.setText(JTFResultado.getText() + d);
        }
    }

    /** Botão de ponto/vírgula decimal. */
    private void digitarPonto() {
        if (novoNumero) {
            JTFResultado.setText("0.");
            novoNumero = false;
        } else if (!JTFResultado.getText().contains(".")) {
            JTFResultado.setText(JTFResultado.getText() + ".");
        }
    }

    /** Botões + - * / : guarda o primeiro valor e a operação escolhida. */
    private void escolherOperacao(String novaOp) {
        if (!JTFResultado.getText().isEmpty()) {

            if (!op.isEmpty() && !novoNumero) {
                calcular();
            }

            valor1 = Double.parseDouble(JTFResultado.getText());
        }

        op = novaOp;
        novoNumero = true;
    }

    /** Botão = : faz a conta com valor1, a operação e o valor do visor. */
    private void calcular() {
        if (op.isEmpty() || JTFResultado.getText().isEmpty()) {
            return;
        }

        double valor2 = Double.parseDouble(JTFResultado.getText());
        double resultado = 0;

        if (op.equals("Soma")) {
            resultado = valor1 + valor2;

        } else if (op.equals("Subtracao")) {
            resultado = valor1 - valor2;

        } else if (op.equals("Multiplicacao")) {
            resultado = valor1 * valor2;

        } else if (op.equals("Potencia")) {
            resultado = Math.pow(valor1, valor2);

        } else if (op.equals("Divisao")) {

            if (valor2 == 0) {
                JTFResultado.setText("Erro");
                op = "";
                novoNumero = true;
                return;
            }

            resultado = valor1 / valor2;
        }

        if (resultado == (long) resultado) {
            JTFResultado.setText(String.valueOf((long) resultado));
        } else {
            JTFResultado.setText(String.valueOf(resultado));
        }

        valor1 = resultado;
        op = "";
        novoNumero = true;
    }

    /**
     * Janela da calculadora.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 464, 498);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Cor de fundo
        frame.getContentPane().setBackground(FUNDO);

        // Nome do aluno - parte superior
        JLabel lblNome = new JLabel(NOME);
        lblNome.setBounds(47, 8, 339, 20);
        lblNome.setFont(new Font("Arial", Font.BOLD, 12));
        lblNome.setForeground(VERMELHO);
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblNome);

        // RA do aluno - abaixo do nome
        JLabel lblRA = new JLabel("RA: " + RA);
        lblRA.setBounds(47, 29, 339, 20);
        lblRA.setFont(new Font("Arial", Font.PLAIN, 11));
        lblRA.setForeground(TEXTO);
        lblRA.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblRA);

        // Visor posicionado abaixo do nome e RA
        JTFResultado = new JTextField();
        JTFResultado.setBounds(47, 56, 339, 73);
        frame.getContentPane().add(JTFResultado);
        JTFResultado.setColumns(10);

        // Estilo do visor
        JTFResultado.setFont(new Font("Arial", Font.BOLD, 32));
        JTFResultado.setHorizontalAlignment(SwingConstants.RIGHT);
        JTFResultado.setForeground(TEXTO);
        JTFResultado.setBackground(Color.WHITE);
        JTFResultado.setBorder(BorderFactory.createLineBorder(VERMELHO, 2));

        // Números
        criarBotao("0", 135, 366, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("0");
            }
        });

        criarBotao("1", 61, 301, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("1");
            }
        });

        criarBotao("2", 135, 302, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("2");
            }
        });

        criarBotao("3", 214, 301, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("3");
            }
        });

        criarBotao("4", 61, 231, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("4");
            }
        });

        criarBotao("5", 135, 235, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("5");
            }
        });

        criarBotao("6", 214, 235, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("6");
            }
        });

        criarBotao("7", 61, 167, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("7");
            }
        });

        criarBotao("8", 135, 167, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("8");
            }
        });

        criarBotao("9", 214, 167, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitar("9");
            }
        });

        // Operações
        criarBotao("=", 275, 366, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcular();
            }
        });

        criarBotao("-", 339, 366, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escolherOperacao("Subtracao");
            }
        });

        criarBotao("+", 339, 301, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escolherOperacao("Soma");
            }
        });

        criarBotao("*", 339, 235, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escolherOperacao("Multiplicacao");
            }
        });

        criarBotao("/", 339, 167, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escolherOperacao("Divisao");
            }
        });

        criarBotao("^", 278, 235, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escolherOperacao("Potencia");
            }
        });

        // Ponto decimal
        criarBotao(",", 214, 366, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitarPonto();
            }
        });

        criarBotao(".", 61, 366, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitarPonto();
            }
        });

        // Limpar
        criarBotao("C", 278, 301, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTFResultado.setText("");
                valor1 = 0;
                op = "";
                novoNumero = true;
            }
        });
    }
}

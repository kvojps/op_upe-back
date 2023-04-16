import pandas as pd

df = pd.read_excel('projetos.xlsx')

df['2. Campus / Unidade / Curso: *'] = df['2. Campus / Unidade / Curso: *'].astype(str).str.lower()
df['curso'] = ''
cursos = ['Ciências Biológicas', 'Ciências Sociais', 'Computação', 'Educação Física', 'Geografia', 'História', 'Letras com habilitação em língua portuguesa e espanhola', 'Letras com habilitação em língua portuguesa e inglesa', 'Letras com habilitação em língua portuguesa e suas literaturas', 'Matemática', 'Pedagogia', 'Administração', 'Ciências biológicas', 'Direito', 'Educação Física', 'Enfermagem', 'Engenharia civil', 'Engenharia da computação', 'Engenharia de controle e automação', 'Engenharia de software', 'Engenharia elétrica de telecomunicações', 'Engenharia elétrica eletrônica', 'Engenharia elétrica eletrotécnica', 'Engenharia mecânica', 'Física de materiais', 'Fisioterapia', 'Medicina', 'Nutrição', 'Odontologia', 'Psicologia', 'Saúde coletiva', 'Serviço social', 'Sistemas de informação', 'Terapia ocupacional', 'Tecnologia em logística']
for index, row in df.iterrows():
    for curso in cursos:
        if curso.lower() in row['2. Campus / Unidade / Curso: *']:
            df.at[index, 'curso'] = curso
            break
counts = df['curso'].value_counts()
print(counts)

df = df.drop('Carimbo de data/hora', axis=1)
df = df.drop('1.1 E-mail do proponente:', axis=1)
df = df.drop('7. Coordenador(es) / CPF / Carga horária de participação:', axis=1)
df = df.drop('8. Professor(es) / CPF / Carga horária de participação:', axis=1)
df = df.drop('9. Estudante(s) extensionista(s) / CPF / Carga Horária de participação:', axis=1)
df = df.drop('10. Técnico-administrativo(s) / CPF / Carga Horária de participação:', axis=1)
df = df.drop('Confirme o Edital no qual a atividade foi cadastrada:', axis=1)
df = df.drop('Palavras chave', axis=1)
df = df.drop('5. Período de execução da atividade:', axis=1)
df = df.drop('15. Descreva os principais fatos ocorridos antes e durante a atividade:', axis=1)
df = df.drop('16. Elabore uma interpretação crítica desta atividade:', axis=1)

df = df.rename(columns={'Categoria do projeto': 'areaTematica'})
df = df.rename(columns={'3. Modalidade:': 'modalidade'})
df = df.rename(columns={'1. Título da atividade:': 'titulo'})
df = df.rename(columns={'11. Resumo da atividade:': 'resumo'})
df = df.rename(columns={'Introdução': 'introducao'})
df = df.rename(columns={'6. Fundamentação': 'fundamentacao'})
df = df.rename(columns={'12. Objetivo(s) da atividade:': 'objetivos'})
df = df.rename(columns={'17. Apresente as conclusões desta atividade:': 'conclusao'})
df = df.rename(columns={'18. Memória Visual que evidencie a realização da atividade (fotos, vídeos, reportagens, links, dentre outros) *': 'memoriaVisual'})
df = df.rename(columns={'4. Em que ano iniciou esta atividade:': 'dataInicio'})
df = df.rename(columns={'Data Finalização das atividades': 'dataFim'})
df = df.rename(columns={'13. Descreva o público alvo atendido:': 'publicoAlvo'})
df = df.rename(columns={'14. Nº de pessoas atendidas:': 'pessoasAtendidas'})
df = df.rename(columns={'Suporte Financeiro': 'suporteFinanceiro'})
df['usuarioId'] = 0
df = df.rename(columns={'2. Campus / Unidade / Curso: *': 'campusId'})

colunas = df.columns.tolist()
colunas.insert(0, colunas.pop(colunas.index('areaTematica')))
colunas.insert(1, colunas.pop(colunas.index('modalidade')))
colunas.insert(2, colunas.pop(colunas.index('titulo')))
colunas.insert(3, colunas.pop(colunas.index('resumo')))
colunas.insert(4, colunas.pop(colunas.index('introducao')))
colunas.insert(5, colunas.pop(colunas.index('fundamentacao')))
colunas.insert(6, colunas.pop(colunas.index('objetivos')))
colunas.insert(7, colunas.pop(colunas.index('conclusao')))
colunas.insert(8, colunas.pop(colunas.index('memoriaVisual')))
colunas.insert(9, colunas.pop(colunas.index('dataInicio')))
colunas.insert(10, colunas.pop(colunas.index('dataFim')))
colunas.insert(11, colunas.pop(colunas.index('publicoAlvo')))
colunas.insert(12, colunas.pop(colunas.index('pessoasAtendidas')))
colunas.insert(13, colunas.pop(colunas.index('suporteFinanceiro')))
colunas.insert(14, colunas.pop(colunas.index('usuarioId')))
colunas.insert(15, colunas.pop(colunas.index('campusId')))
df = df[colunas]

#areaTematica
df['areaTematica'] = df['areaTematica'].replace({'Pesquisa': 'PESQUISA', 'Extensão': 'EXTENSAO'})

#modalidade
df['modalidade'] = df['modalidade'].str.split('/').str[0]
df['modalidade'] = df['modalidade'].str.strip()
df['modalidade'] = df['modalidade'].str.upper()
df = df.drop(df[df['modalidade'] == 'PRESTAÇÃO DE SERVIÇOS'].index)

df.to_excel('novo_arquivo.xlsx', index=False)





theory trabalhogrupo8
imports Main
begin
(* Projeto 2: Grupo 8: Fábio Melo, Natália Barreto, Luigge Lena *)
(* Questão 1: \<not>Q \<Longrightarrow> \<not>P \<turnstile>  P \<Longrightarrow> Q *)    

theorem questao1 : 
  assumes nqnp : "\<not>Q \<longrightarrow> \<not>P"
  shows "P \<longrightarrow> Q"

proof -
    have imp1:"P\<Longrightarrow>Q"
      proof -
      assume p:"P"
      have imp2:"\<not>Q\<Longrightarrow>False"
        proof -
        assume nq:"\<not>Q"
        from nqnp and nq have np:"\<not>P" by(rule impE)
        from np and p show "False" by(rule notE)
        qed
      from imp2 have nnq:"\<not>\<not>Q"by(rule notI)
      from nnq show "Q" by(rule notnotD)
      qed
     from imp1 show "P\<longrightarrow>Q" by(rule impI) 
qed

(* Questão 2:  P \<longrightarrow> Q, P \<longrightarrow> R \<turnstile> P \<longrightarrow> Q \<and> R *)

theorem questao2:
  assumes p_imp_q: "P \<longrightarrow> Q"
      and p_imp_r: "P \<longrightarrow> R"
      shows "P \<longrightarrow> Q \<and> R"
  
  proof-
     have pqr: "P \<Longrightarrow> Q \<and> R"
      proof-
       assume p: "P"
           from p_imp_q and p have q: "Q" by (rule impE)
           from p_imp_r and p have r: "R" by (rule impE)
           from q and r show "Q \<and> R" by (rule conjI)
       qed 
       
       from this show "P \<longrightarrow> Q \<and> R" by (rule impI)
   
qed

end
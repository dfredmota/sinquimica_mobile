package br.com.sindquimicace.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class WsDao {


	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	 

	
	public static Usuario loginApp(String dir, String user, String password, String token) {
		Connection con = null;
		PreparedStatement ps = null;
		Usuario usuario = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from usuario where login = ? and password = ? and perfil_id = ?");
			ps.setString(1, user);
			ps.setString(2, password);
			ps.setInt(3, 3);

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			if (rs.next()) {
								
				usuario = new Usuario();
				
				usuario.setEndereco(new Endereco());
				usuario.setEmpresa(new EmpresaAssociada());
				usuario.setPerfil(new Perfil());
				
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setDtNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setTelefones(rs.getString("telefones"));
				usuario.setSite(rs.getString("site"));
				usuario.getEndereco().setId(rs.getInt("endereco_id"));
				usuario.getEmpresa().setId(rs.getInt("empresa_associada_id"));
				usuario.setStatus(rs.getBoolean("status"));
				usuario.getPerfil().setId(rs.getInt("perfil_id"));
				usuario.setEmpresaSistema(rs.getInt("empresa_sistema_id"));
				usuario.setImagemPath(rs.getString("imagem_path"));
				usuario.setImagem(rs.getBytes("imagem"));
				usuario.setLogin(rs.getString("login"));
				usuario.setPassword(rs.getString("password"));

			}
				
			if(usuario.getId() != null)
				atualizaTokenDeUsuario(usuario.getId(),token);
			
			if(usuario.getEndereco().getId() != null)
				usuario.setEndereco(getEndereco(usuario.getEndereco().getId()));			
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return usuario;
		} finally {
			DataConnect.close(con);
		}
		return usuario;
	}
	
	public static Endereco getEndereco(Integer id){
		
		Connection con = null;
		PreparedStatement ps = null;
		Endereco endereco = null;

		try {
			
			con = DataConnect.getConnection();
			ps = con.prepareStatement(" Select * from endereco where id = ? ");
			
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			if (rs.next()) {
								
				endereco = new Endereco();
				
				endereco.setId(rs.getInt("id"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setNumero(rs.getString("numero"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setCep(rs.getString("cep"));
				endereco.setComplemento(rs.getString("complemento"));

			}
				
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return endereco;
		} finally {
			DataConnect.close(con);
		}
		return endereco;		

	}
	
	/**
	 * 1 - confirmou presença
	 * 2 - não vai
	 * 
	 * 
	 * @param evento
	 */
	public static void confirmaPresencaEventoUsuario(Evento evento){
		
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
		 try {
		 
        String sql = "update evento_usuario set confirmou=?,visualizou=? where evento_id="+evento.getId()+
        		" and usuario_id="+evento.getIdUsuarioConfirmou();
		
        con = DataConnect.getConnection();

        ps = con.prepareStatement(sql);
        
        if(evento.getConfirmou())
        ps.setInt(1, 1);
        else
       	ps.setInt(1, 2);
        
        ps.setBoolean(2, true);
        
        System.out.println(ps);
        
        ps.executeUpdate();        
        
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (ps != null)
	                ps.close();
	            if (con != null)
	                con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	    }
		
		
	}
	
	public static void atualizaTokenDeUsuario(Integer idUsuario,String token){
		
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
		 try {
		 
         String sql = "update usuario set token_app='"+token+"' where id="+idUsuario;
		
         con = DataConnect.getConnection();

         ps = con.prepareStatement(sql);
         
         System.out.println(ps);
         
         ps.executeUpdate();
         
         
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (ps != null)
	                ps.close();
	            if (con != null)
	                con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	    }
		
		
	}
	
	public static Usuario insertUsuario(String dir, Usuario usuario) {
		
		 Integer idUsuario = null;
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;

	        try {

	            String sql = "insert into usuario(nome,data_nascimento,endereco_id,email,"
	            		+ "telefones,site,empresa_associada_id,created_at,status,login,password,perfil_id,"
	            		+ "empresa_sistema_id,imagem_path,imagem,cadastro_app) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning id;";

	            con = DataConnect.getConnection();

	            ps = con.prepareStatement(sql);

	            ps.setString(1,usuario.getNome());
	            ps.setDate(2, new java.sql.Date(usuario.getDtNascimento().getTime()));
	            ps.setInt(3, insertEndereco(usuario.getEndereco()));
	            ps.setString(4, usuario.getEmail());
	            ps.setString(5, usuario.getTelefones());
	            ps.setString(6,usuario.getSite());
	            ps.setInt(7,usuario.getEmpresa().getId());
	            ps.setDate(8, new java.sql.Date(new Date().getTime()));
	            ps.setBoolean(9, false);
	            ps.setString(10, usuario.getLogin());
	            ps.setString(11, usuario.getPassword());
	            ps.setInt(12, usuario.getPerfil().getId());
	            ps.setInt(13, usuario.getEmpresa().getEmpresaSistema());
      
	            String pathImagem = saveImage(dir,usuario);	   
	            
	            usuario.setImagemPath(pathImagem);
	            
	            ps.setString(14, usuario.getImagemPath());  
	            
	            ps.setBytes(15, usuario.getImagem());
	            ps.setBoolean(16, true);
	            
	            System.out.println(ps);
	                        
	            rs = ps.executeQuery();

	            if (rs.next())
	            	idUsuario = rs.getInt("id");

	            if (idUsuario != null){
	               usuario.setId(idUsuario);	                
	            }


	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null)
	                    rs.close();
	                if (ps != null)
	                    ps.close();
	                if (con != null)
	                    con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        }

	        return usuario;	
		
	}
	
	public static Usuario alterarUsuario(String dir, Usuario usuario) {
		
		 Integer idUsuario = null;
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;

	        try {

	            String sql = "update usuario set nome=?,data_nascimento=?,endereco_id=?,email=?,"
	            		+ "telefones=?,site=?,empresa_associada_id=?,imagem=?,login = ?,password = ? where id="+usuario.getId();

	            con = DataConnect.getConnection();

	            ps = con.prepareStatement(sql);

	            ps.setString(1,usuario.getNome());
	            ps.setDate(2, new java.sql.Date(usuario.getDtNascimento().getTime()));
	            ps.setInt(3, insertEndereco(usuario.getEndereco()));
	            ps.setString(4, usuario.getEmail());
	            ps.setString(5, usuario.getTelefones());
	            ps.setString(6,usuario.getSite());
	            ps.setInt(7,usuario.getEmpresa().getId());
	            ps.setBytes(8, usuario.getImagem());
	            ps.setString(9,usuario.getLogin());
	            ps.setString(10,usuario.getPassword());
	            
	            System.out.println(ps);
	                        
	            rs = ps.executeQuery();

	            if (rs.next())
	            	idUsuario = rs.getInt("id");

	            if (idUsuario != null){
	               usuario.setId(idUsuario);	                
	            }


	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null)
	                    rs.close();
	                if (ps != null)
	                    ps.close();
	                if (con != null)
	                    con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        }

	        return usuario;	
		
	}
	
	
	
	private static Integer insertEndereco(Endereco endereco){
		
		 Integer idEndereco = null;
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
		 try {
		 
         String sql = "insert into endereco(logradouro,numero,bairro,cidade,"
         		+ "cep,complemento,created_at,empresa_sistema_id) values(?,?,?,?,?,?,?,?) returning id;";
         
         
         con = DataConnect.getConnection();

         ps = con.prepareStatement(sql);

         ps.setString(1,endereco.getLogradouro());
         ps.setString(2, endereco.getNumero());
         ps.setString(3, endereco.getBairro());
         ps.setString(4, endereco.getCidade());
         ps.setString(5, endereco.getCep());
         ps.setString(6,endereco.getComplemento());
         ps.setDate(7, new java.sql.Date(new Date().getTime()));
         ps.setInt(8, endereco.getEmpresaSistema());
         
         System.out.println(ps);

         rs = ps.executeQuery();

         if (rs.next())
        	 idEndereco = rs.getInt("id");

         if (idEndereco != null){
            return    idEndereco;            
         }


     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         try {
             if (rs != null)
                 rs.close();
             if (ps != null)
                 ps.close();
             if (con != null)
                 con.close();
         } catch (SQLException e) {
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }

     }
		
		return idEndereco;
	}
	
	public static List<EmpresaAssociada> listaEmpresasAssociadas() {
		Connection con = null;
		PreparedStatement ps = null;
		List<EmpresaAssociada> lista = new ArrayList<EmpresaAssociada>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from empresa_associada where deleted_at is null");

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			while(rs.next()) {
								
				EmpresaAssociada emp = new EmpresaAssociada();
				
				emp.setId(rs.getInt("id"));
				emp.setNomeFantasia(rs.getString("nome_fantasia"));
				emp.setEmpresaSistema(rs.getInt("empresa_sistema_id"));
				
				lista.add(emp);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	public static Usuario reenvioDeSenha(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		Usuario usuario = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from usuario where email = ?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			if (rs.next()) {
								
				usuario = new Usuario();
								
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setStatus(rs.getBoolean("status"));
				usuario.setLogin(rs.getString("login"));
				usuario.setPassword(rs.getString("password"));

			}
			

			reenvioDeSenhaProEmail(usuario);
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return usuario;
		} finally {
			DataConnect.close(con);
		}
		return usuario;
	}
	
	public static void reenvioDeSenhaProEmail(Usuario usuario) {

		try {

			final String username = "2biportal@gmail.com";
			final String password = "eaccsl2017";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("2biportal@gmail.com"));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));

			message.setSubject("Reenvio de Senha - Sindquimica!","UTF-8");

			message.setText("Ola ," + usuario.getNome() + "\n\n Segue sua senha de acesso ao app!:"
					+ "\n\n Login:" + usuario.getLogin()
					+" \n\n Senha:" + usuario.getPassword()
					+ " \n\n\n Atenciosamente, Equipe App Sindquimica.","UTF-8");
			

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static List<Evento> listaEventosDeUsuario(Usuario usuario){
		
		Connection con = null;
		PreparedStatement ps = null;
		List<Evento> lista = new ArrayList<Evento>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select ev.id ,ev.descricao,ev.inicio,ev.fim,ev.empresa_sistema_id,ev.local,ev_user.usuario_id,ev.status "); 
		sql.append(" from evento as ev,evento_usuario as ev_user where ev_user.usuario_id = "+usuario.getId());
		sql.append(" and ev_user.evento_id = ev.id order by ev.created_at USING >");
		
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);
			
			while(rs.next()) {
				
				Evento evento = new Evento();
				
				evento.setId(rs.getInt("id"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setInicio(rs.getTimestamp("inicio"));
				evento.setFim(rs.getTimestamp("fim"));
				evento.setLocal(rs.getString("local"));
				evento.setStatus(rs.getBoolean("status"));				
							
				lista.add(evento);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
		

	}
	
	
	public static List<Mensagem> 	listaMensagensUsuario(String diretorio, Usuario usuario) {
		
		Connection con = null;
		PreparedStatement ps = null;
		List<Mensagem> lista = new ArrayList<Mensagem>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(" select msg.usuario_id as usuario_send_id, msg.conteudo,msg.id,msg.created_at "+
						" from mensagem msg,mensagem_usuario msg_user where msg_user.usuario_id ="+usuario.getId()+
						" and msg.id = msg_user.mensagem_id and msg_user.empresa_sistema_id="+usuario.getEmpresaSistema() +" ORDER BY created_at USING >");

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			while(rs.next()) {
								
				Mensagem msg = new Mensagem();
				
				msg.setId(rs.getInt("id"));
				msg.setConteudo(rs.getString("conteudo"));
				msg.setCreatedAt(rs.getTimestamp("created_at"));
				
				Usuario user = getUsuario(diretorio,rs.getInt("usuario_send_id"));
				
				if(user != null){
					
					msg.setUsuario(user);
				}
				
				lista.add(msg);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	public static List<Mensagem> 	listaMensagensGrupo(Grupo grupo) {
		
		Connection con = null;
		PreparedStatement ps = null;
		List<Mensagem> lista = new ArrayList<Mensagem>();

		try {
			con = DataConnect.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("select msg.usuario_id as usuario_send_id, "); 

			sql.append("msg.conteudo,msg.id,msg.created_at ,msg_grupo.grupo_id ");
			
			sql.append("from mensagem msg,mensagem_grupo msg_grupo where msg_grupo.grupo_id ="+grupo.getId()+" ");

			sql.append(" and msg.id = msg_grupo.mensagem_id	and msg_grupo.empresa_sistema_id="+grupo.getEmpresaSistema() +" ORDER BY created_at USING > ");
			
			
			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);

			while(rs.next()) {
								
				Mensagem msg = new Mensagem();
				
				msg.setId(rs.getInt("id"));
				msg.setConteudo(rs.getString("conteudo"));
				msg.setCreatedAt(rs.getTimestamp("created_at"));
				
				Usuario user = getUsuario("",rs.getInt("usuario_send_id"));
				
				if(user != null){
					
					msg.setUsuario(user);
				}
				
				lista.add(msg);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	public Mensagem salvarMensagem(Mensagem msg){
		
		Integer idMensagem = insertMensagem(msg);
		
		msg.setId(idMensagem);
		
		// salva os usuarios da mensagem
		if(msg.getUsuarios() != null && !msg.getUsuarios().isEmpty()){
			
			for(Usuario user : msg.getUsuarios()){
				
				insertUsuariosMensagem(idMensagem,user);
			}			
		}
		
		// salva os grupos da mensagem
		if(msg.getGrupos() != null && !msg.getGrupos().isEmpty()){
			
			for(Grupo grupo : msg.getGrupos()){
				
				insertGrupoMensagem(idMensagem,grupo);
			}			
		}		
		
		return msg;
	}
	
	public static void insertGrupoMensagem(Integer idMensagem,Grupo grupo){
		
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
	 try {
		 
      String sql = "insert into mensagem_grupo(grupo_id, mensagem_id,empresa_sistema_id) values(?,?,?) returning id;";
      
      con = DataConnect.getConnection();

      ps = con.prepareStatement(sql);

      ps.setInt(1,grupo.getId());
      ps.setInt(2, idMensagem);
      ps.setInt(3,grupo.getEmpresaSistema());

      rs = ps.executeQuery();

	   } catch (Exception e) {
	       e.printStackTrace();
	   } finally {
	       try {
	           if (rs != null)
	               rs.close();
	           if (ps != null)
	               ps.close();
	           if (con != null)
	               con.close();
	       } catch (SQLException e) {
	           e.printStackTrace();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }

	   }

	}
	
	public static void insertUsuariosMensagem(Integer idMensagem, Usuario usuario){
		
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
	 try {
		 
       String sql = "insert into mensagem_usuario(usuario_id, mensagem_id,empresa_sistema_id) values(?,?,?) returning id;";
       
       con = DataConnect.getConnection();

       ps = con.prepareStatement(sql);

       ps.setInt(1,usuario.getId());
       ps.setInt(2, idMensagem);
       ps.setInt(3,usuario.getEmpresaSistema());

       rs = ps.executeQuery();
       
       System.out.println(ps);


	   } catch (Exception e) {
	       e.printStackTrace();
	   } finally {
	       try {
	           if (rs != null)
	               rs.close();
	           if (ps != null)
	               ps.close();
	           if (con != null)
	               con.close();
	       } catch (SQLException e) {
	           e.printStackTrace();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }

	   }

	}

	
	public static Integer insertMensagem(Mensagem mensagem){
		
		 Integer idMsg = null;
		 PreparedStatement ps = null;
		 Connection con = null;
		 ResultSet rs = null;
		 
		 try {
		 
        String sql = "insert into mensagem(conteudo, created_at,usuario_id, file_name,"+ 
            "empresa_sistema_id) values(?,?,?,?,?) returning id;";
        
        
        con = DataConnect.getConnection();

        ps = con.prepareStatement(sql);

        ps.setString(1,mensagem.getConteudo());
        ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
        ps.setInt(3, mensagem.getUsuario().getId());
        ps.setString(4, " ");
        ps.setInt(5,mensagem.getUsuario().getEmpresaSistema());

        rs = ps.executeQuery();
        
        System.out.println(ps);

        if (rs.next())
        	idMsg = rs.getInt("id");

        if (idMsg != null){
        	mensagem.setId(idMsg);            
        }


    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
		 
		 // salvar usuarios e grupos
		 if(mensagem.getUsuarios() != null && !mensagem.getUsuarios().isEmpty()){
			 
			 for(Usuario user : mensagem.getUsuarios()){
				 
				 insertUsuariosMensagem(idMsg, user); 
				 
			 }
			 
		 }
		 
		 if(mensagem.getGrupos() != null && !mensagem.getGrupos().isEmpty()){
			 
			 for(Grupo grupo : mensagem.getGrupos()){
				 
				 insertGrupoMensagem(idMsg, grupo); 
				 
			 }
			 
		 }
		 
		
		return idMsg;
	}
	
	public static List<Grupo> listaGrupos() {
		
		Connection con = null;
		PreparedStatement ps = null;
		List<Grupo> lista = new ArrayList<Grupo>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(" select * from grupo ");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
								
				Grupo grupo = new Grupo();
				
				grupo.setId(rs.getInt("id"));
				grupo.setNome(rs.getString("nome"));
				grupo.setEmpresaSistema(rs.getInt("empresa_sistema_id"));
				
				lista.add(grupo);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	
	
	public static List<Grupo> listaGruposUsuario(Usuario usuario) {
		
		Connection con = null;
		PreparedStatement ps = null;
		List<Grupo> lista = new ArrayList<Grupo>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(" select gp.id,gp.nome as nomeGrupo,gp.empresa_sistema_id as empresaSistemaId "+ 
									" from grupo gp,grupo_usuarios gu where gu.usuario_id = "+usuario.getId()+
									" and gu.grupo_id = gp.id and gp.empresa_sistema_id="+usuario.getEmpresaSistema());

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
								
				Grupo grupo = new Grupo();
				
				grupo.setId(rs.getInt("id"));
				grupo.setNome(rs.getString("nomegrupo"));
				grupo.setEmpresaSistema(rs.getInt("empresasistemaid"));
				
				lista.add(grupo);

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	private static String saveImage(String dir, Usuario usuario){
		
		if (usuario.getImagem() != null) {       	        	
		
			File file1 = new File(dir+"/images/",usuario.getNome()+"_app");
			usuario.setImagemPath(file1.getName());
			try {
				FileOutputStream fos = new FileOutputStream(file1);
				fos.write(usuario.getImagem());
				fos.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			usuario.setImagemPath("");
		}
		
		return usuario.getImagemPath();
		
	}
	
	private static byte[] readImage(String dir, Usuario usuario){
		
		byte fileContent[]=null;
		  	        	
		
		File file = new File(dir+"/images/", usuario.getImagemPath());
		
		FileInputStream fin = null;

			
		try {
			
			            // create FileInputStream object
			
			            fin = new FileInputStream(file);
			 
			
			            fileContent = new byte[(int)file.length()];
						             
			
			            // Reads up to certain bytes of data from this input stream into an array of bytes.
			
			            fin.read(fileContent);
			
			            //create string from byte array
			
			            String s = new String(fileContent);
			
			            System.out.println("File content: " + s);
						        }
			
			        catch (FileNotFoundException e) {
			
			            System.out.println("File not found" + e);
			
			        }
			
			        catch (IOException ioe) {
			
			            System.out.println("Exception while reading file " + ioe);
			
			        }
			
			        finally {
			
			            // close the streams using close method
			
			            try {
			
			                if (fin != null) {
			
			                    fin.close();
			
			                }
			
			            }
			
			            catch (IOException ioe) {
			
			                System.out.println("Error while closing stream: " + ioe);
			
			            }
			
			        }
		
		return fileContent;
	}
	
	public static List<Usuario> listaUsuarios() {
		Connection con = null;
		PreparedStatement ps = null;
		List<Usuario> lista = new ArrayList<Usuario>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from usuario where cadastro_app=true");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
								
				Usuario usuario = new Usuario();
				
				usuario.setEndereco(new Endereco());
				usuario.setEmpresa(new EmpresaAssociada());
				usuario.setPerfil(new Perfil());
				
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setDtNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setTelefones(rs.getString("telefones"));
				usuario.setSite(rs.getString("site"));
				usuario.getEndereco().setId(rs.getInt("endereco_id"));
				usuario.getEmpresa().setId(rs.getInt("empresa_associada_id"));
				usuario.setStatus(rs.getBoolean("status"));
				usuario.getPerfil().setId(rs.getInt("perfil_id"));
				usuario.setEmpresaSistema(rs.getInt("empresa_sistema_id"));
				usuario.setImagemPath(rs.getString("imagem_path"));
				usuario.setImagem(rs.getBytes("imagem"));
				usuario.setToken(rs.getString("token_app"));
				
				lista.add(usuario);

			}

		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return lista;
		} finally {
			DataConnect.close(con);
		}
		return lista;
	}
	
	private static Usuario getUsuario(String dir, Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		Usuario usuario = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from usuario where id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
								
				usuario = new Usuario();
				
				usuario.setEndereco(new Endereco());
				usuario.setEmpresa(new EmpresaAssociada());
				usuario.setPerfil(new Perfil());
				
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setDtNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setTelefones(rs.getString("telefones"));
				usuario.setSite(rs.getString("site"));
				usuario.getEndereco().setId(rs.getInt("endereco_id"));
				usuario.getEmpresa().setId(rs.getInt("empresa_associada_id"));
				usuario.setStatus(rs.getBoolean("status"));
				usuario.getPerfil().setId(rs.getInt("perfil_id"));
				usuario.setEmpresaSistema(rs.getInt("empresa_sistema_id"));
				usuario.setImagemPath(rs.getString("imagem_path"));
				usuario.setImagem(rs.getBytes("imagem"));

			}
			

			
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return usuario;
		} finally {
			DataConnect.close(con);
		}
		return usuario;
	}

	public static Evento listaEventoUsuario(Integer[] ids){
		
		Connection con = null;
		PreparedStatement ps = null;
		Evento evento = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select ev.id ,ev.descricao,ev.inicio,ev.fim,ev.empresa_sistema_id,ev.local,ev.imagem,ev_user.usuario_id,ev.status,confirmou "); 
		sql.append(" from evento as ev,evento_usuario as ev_user where ev_user.usuario_id = "+ids[0]);
		sql.append(" and ev_user.evento_id ="+ids[1]+" and ev_user.evento_id = ev.id");
		
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			
			System.out.println(ps);
			
			while(rs.next()) {
				
				evento = new Evento();
				
				evento.setId(rs.getInt("id"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setInicio(rs.getTimestamp("inicio"));
				evento.setFim(rs.getTimestamp("fim"));
				evento.setLocal(rs.getString("local"));
				evento.setIdUsuarioConfirmou(rs.getInt("usuario_id"));
				evento.setImagem(rs.getBytes("imagem"));
				
				Integer confirmou = rs.getInt("confirmou");
				
				if(confirmou != null){
					
					if(confirmou == 1)
						evento.setConfirmou(true);
					else
						evento.setConfirmou(false);					
				}
							

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return evento;
		} finally {
			DataConnect.close(con);
		}
		return evento;
		

	}
	
}
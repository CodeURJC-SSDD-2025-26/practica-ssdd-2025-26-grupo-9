# OneDeck
## 👥 Miembros del Equipo
| Nombre y Apellidos | Correo URJC | Usuario GitHub |
|:--- |:--- |:--- |
| [Pablo León Molero] | p.leon.2019@alumnos.urjc.es | Pol1705 |
| [Aarón Fernández Lijó] | a.fernandezli.2020@alumnos.urjc.es | zZAiron |
| [Sergio Espinosa Robles] | s.espinosa.2020@alumnos.urjc.es | SergiOnLive3 |


---

## 🎭 **Preparación: Definición del Proyecto**

### **Descripción del Tema**
Nuestra pagina web consiste en un pequeño foro en el que cada usuario podrá crear mazos libremente para el TCG(Trading Card Game) de One Piece, permitiendo agregar amigos, y comentar sobre los mazos de otras personas, los administradores, podrán tambien añadir, editar y eliminar cartas de modo que pueda estar al dia de los cambios del juego real.

### **Entidades**
Indicar las entidades principales que gestionará la aplicación y las relaciones entre ellas:

1. Usuario
2. Mazo
3. Carta
4. Comentario

**Relaciones entre entidades:**
- Usuario - Mazo: Un usuario puede tener múltiples mazos (1:N)
- Mazo - Carta: Un mazo puede contener múltiples Cartas y una carta puede estar en múltiples Mazos (N:M)
- Mazo - Comentario: Un mazo puede tener multiples comentarios (N:1)
- Usuario - Usuario: Todos los usuarios podran tener amigos y podran verlos desde su pagina de perfil(N:M)

### **Permisos de los Usuarios**
Describir los permisos de cada tipo de usuario e indicar de qué entidades es dueño:

* **Usuario Anónimo**: 
  - Permisos: Unicamente podra navegar por la pagina a traves de la información pública, ver los mazos y comentarios posteados por la comunidad y visitar los perfiles de los usuarios. Podra registrarse para pasar a ser usuario registrado pero no podra postear ni comentar sobre mazos.
  - No es dueño de ninguna entidad

* **Usuario Registrado**: 
  - Permisos: Un usuario registrado tendrá todos los permisos de un usuario anónimo y además, podra agregar amistades, postear mazos, comentar sobre ellos y modificar su propio perfil.
  - Es dueño de su perfil, de sus mazos y sus comentarios.

* **Administrador**: 
  - Permisos: Todos los del usuario registrado y ademas, podrá moderar los post de la web independientemente de su dueño, permitiendo eliminar mazos o comentarios, tambien podra modificar las cartas, crearlas o (aun no decidido) eliminarlas, y finalmente, tendrá control absoluto sobre los usuarios de la web, permitiendo realizar cualquier cambio sobre el resto de usuarios que no sean tambien administradores.
  - Es dueño de las mismas entidades que el usuario registrado, cartas y resto de usuarios no administradores.

### **Imágenes**
Indicar qué entidades tendrán asociadas una o varias imágenes:

- **[Cartas]**: Una carta contiene una imagen que define su aspecto
- **[Usuario]**: Un usuario tendra una imagen asociada que servirá de foto de perfil.

### **Gráficos**
Indicar qué información se mostrará usando gráficos y de qué tipo serán:

- **Gráfico 1**: Cartas preferidas: Cada usuario tendrá en su perfil un gráfico radial que mostrará la proporción de uso de cartas en sus mazos, permitiendo asi ver cual/es son sus cartas preferidas.

### **Tecnología Complementaria**
Indicar qué tecnología complementaria se empleará:

- NO DECIDIDO TODAVIA
- [Ej: Envío de correos electrónicos automáticos mediante JavaMailSender]
- [Ej: Generación de PDFs de facturas usando iText o similar]

### **Algoritmo o Consulta Avanzada**
Indicar cuál será el algoritmo o consulta avanzada que se implementará:

- **Algoritmo/Consulta**: [Ej: Sistema de recomendaciones basado en el historial de compras del usuario]
- **Descripción**: [Ej: Analiza los productos comprados previamente y sugiere productos similares o complementarios utilizando filtrado colaborativo]
- **Alternativa**: [Ej: Consulta compleja que agrupe ventas por categoría, mes y región, con cálculo de tendencias]

---

## 🛠 **Práctica 1: Maquetación de páginas web con HTML y CSS**

### **Diagrama de Navegación**
Diagrama que muestra cómo se navega entre las diferentes páginas de la aplicación:

![Diagrama de Navegación](images/navigation-diagram.png)

> [El color rojo representa las acciones que puede hacer el administrador y solo el administrador, el color azul representa las acciones que puede realizar el admin y los usuarios registrados, y por ultimo en verde estan las acciones que cualquier usuario puede hacer aunque no este registrado]

### **Capturas de Pantalla y Descripción de Páginas**

#### **1. Página Principal / Home**
![Página Principal](screenshots/home.png)

> Pagina que unicamente da la bienvenida a la pagina, teniendo una barra superior que permite navegar a todas las distintas paginas.

#### **2. Página Registro / Login**
![Página de Registro/Inicio de Sesion](screenshots/login1.png)

> Pagina que permite al usuario tanto iniciar sesion como registrarse, en funcion de las necesidades del usuario, solo se mostrará a usuarios no logueados.

#### **3. Página Gestion de Cartas / Add Cards**
![Página de gestión de cartas](screenshots/addCards.png)

> Esta pagina permitira a los usuarios administradores gestionar las cartas disponibles en la pagina, tanto crear como editar o eliminarlas.

#### **4. Página Edicion de Cartas / Admin Card**
![Página de edicion de cartas](screenshots/adminCard.png)

> Esta pagina consiste en un formulario completo para poder editar cualquier campo de una carta que el administrador desee.

#### **5. Página Gestion de Usuarios / Admin Users**
![Página de gestion de usuarios](screenshots/adminUsers.png)

> Esta pagina permitirá a los usuarios administradores gestionar a los usuarios registrados en la pagina para poder moderar o regular cualquier incidencia, pudiendo navegar a una pagina de edicion de usuario o eliminarlo por completo.

#### **6. Página Edicion de Usuarios / Edit User Admin**
![Página de edición de usuarios](screenshots/editUserAdmin.png)

> En esta pagina, el administrador dispondrá de un formulario para poder editar cualquier campo de un usuario registrado excepto la imagen(en estamos considerando permitirlo)

#### **7. Página Social / Social**
![Página Social](screenshots/social.png)

> En esta pagina, tanto usuarios registrados como no registrados, se podrá navegar por todos los perfiles de usuarios registrados, permitiendo a estos ultimos tambien agregar amistades para que aparezcan en un futuro en su feed del perfil

#### **8. Mazos de la Comunidad / Decks**
![Mazos de la Comunidad](screenshots/decks.png)

> Esta es la pagina de mayor actividad de la web, en ella todos los usuarios podrán ver los distintos mazos creados por los usuarios para probarlos si gustan en sus partidas y visitar los perfiles de los creadores de dichos mazos, ademas los usuarios registrados podrán acceder a la creación de mazos, eliminación de sus propios mazos y tendrán la posibilidad de comentar sobre los mazos posteados

#### **9. Pagina de Creacion de Mazos / Add Decks**
![Creación de mazos](screenshots/addDeck.png)

> En esta pagina los usuarios dispondrán de las herramientas necesarias para poder crear sus mazos personalizados para postearlos.

#### **10. Perfil / Profile**
![Perfil](screenshots/profile.png)

> En esta pagina se podrá visualizar el perfil de los distintos usuarios, por ahora muestra un perfil estatico, en proximas entregas cargará el perfil que corresponda, permitiendo acceder a la edicion del perfil si es el del usuario logueado.

#### **11. Editar Perfil / Edit User**
![Editar Perfil](screenshots/editUser.png)

> En esta pagina el usuario podra modificar cualquier campo de su perfil, incluida su foto de perfil, que será necesaria ya que al registrarse todos los perfiles tendrán una imagen por defecto.

#### **12. Detalle de la Carta / Card Detail**
![Editar Perfil](screenshots/cardDetail.png)

> En esta pagina el usuario podra ver cualquier campo de la carta,.

### **Participación de Miembros en la Práctica 1**

#### **Alumno 1 - [Pablo León Molero]**

[Principalmente desarrollar codigo de ciertas pantallas como las listadas abajo y funcionalidades de add create edit card, tambien algun arreglo]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [login, navbar,and footer added to all pages, en este commit puse las paginas que en ese momento vi necesarias y las conecte todas con un diseño simple](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/e4e4b94df7655cf9b2b352b264381488a4631420)  | [Casi todos los archivos de ese momento]()  |
|2| [added edit user and admin user añadi con completa funcionalidad estas paginas](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/274851a2495370d19dc2cf356af1aa99162d1ea4)  | [principalmente edit user y admin user](C:\Users\pablo\Documents\GitHub\practica-ssdd-2025-26-grupo-9\proyect\editUser.html)   |
|3| [add cards añadi la pagina add cards](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/4358a02f2046eda30c0ae156b0aa82ac2424d112)  | [addcards](C:\Users\pablo\Documents\GitHub\practica-ssdd-2025-26-grupo-9\proyect\addCards.html)   |
|4| [Edit/Create Card añadi una pagina para crear o editar la entidad, el anterior commit solo mostraba el listado y navega aqui](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/8594d4d547f3f74917a2d0f13448cfd70d2b54a6)  | [admincard](C:\Users\pablo\Documents\GitHub\practica-ssdd-2025-26-grupo-9\proyect\adminCard.html)   |
|5| [Fixes in profile and edituser ](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/de67916894eef33cb0c7982d92e0122b226ff715)  | [profile y edituser](C:\Users\pablo\Documents\GitHub\practica-ssdd-2025-26-grupo-9\proyect\profile.html) |

---

#### **Alumno 2 - Aarón Fernández Lijó**

[Mis principales tareas fueron el desarrollo de  y funcionalidad del perfil(profile) y de la capacidad del sistema de editar el usuario brindándole la posibilidad de modificar sus datos e inclusive cambiando su foto de perfil por una local(editUser)]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Profile mods, commit en el cual implemento y pulo la interfaz del perfil, a pesar de seguir siendo una versión primitiva respecto a la versión final](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/0f3ce0a8b719953e7e280901a3b9fb957577ad34)  | [profile](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/profile.html)   |
|2| [added new style on Login and  editUser buttom created, commit en el que hago el estilo final del perfil en base a estilos prediseñados por mis compañeros](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/40988f40c80c1fc9eb37162bb7b416abe008361f)  | [editUser, profile](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/profile.html) (https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/editUser.html)  |
|3| [Edit Profile Photo using PC archives, commit en el que consigo poder insertar una foto obtenida por el usuario de forma local, básicamente elegir tu foto de perfil](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/5348077dfbde5da4554ce010000505573027afe1)  | [editUser, profile](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/profile.html) (https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/editUser.html)  

---

#### **Alumno 3 - Sergio Espinosa Robles**

Principalmente mis tareas fueron migrar los estilos de la pagina para usar Bootstrap, la realizacion de la pagina los mazos y la pagina detalle de las cartas, el resto fueron tareas secundarias para complementar el trabajo.

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Este fue el primer commit "grande" que hice, en él, borre casi todo el contenido de  nuestro style.css para usar los estilos de Bootstrap](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/c20782651c241a5037403b98352f1872556bc942)  | [style.css](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/style.css)   |
|2| [En este commit añadi la logica de creacion de posts y comentarios, aunque todavia no se reflejen al no haber base de datos](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/49acde8a50cc66dea7948747dc10f290e2178450)  | [addDeck.html](http://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/addDeck.html)   |
|3| [Añadi una breve pagina para mostrar como se verá la pagina social a traves de la que agregar amistades y visitar perfiles](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/d29f8f2d57322e02589de0201627eb468f5779df)  | [social.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/social.html)   |
|4| [Decidimos cambiar la tematica de la pagina por lo que hubo que renombrarla, sin mayor importancia](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/398ed3023a9c2a93519d70e3d2bcc16bdd0abde9)  | [Sin archivo editado principal](*vacio)   |
|5| [Pagina de detalle de las cartas creada](https://github.com/CodeURJC-SSDD-2025-26/ssdd-2025-26-project-base/commit/343596f5421c663ef2659801a9b4bbcbc938d5ec)  | [cardDetail.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/proyect/cardDetail.html)   |

---

## 🛠 **Práctica 2: Web con HTML generado en servidor**

### **Navegación y Capturas de Pantalla**

#### **Diagrama de Navegación**

Solo si ha cambiado.

#### **Capturas de Pantalla Actualizadas**

Solo si han cambiado.

### **Instrucciones de Ejecución**

#### **Requisitos Previos**
- **Java**: versión 21 o superior
- **Maven**: versión 3.8 o superior
- **MySQL**: versión 8.0 o superior
- **Git**: para clonar el repositorio

#### **Pasos para ejecutar la aplicación**

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/[usuario]/[nombre-repositorio].git
   cd [nombre-repositorio]
   ```

2. **AQUÍ INDICAR LO SIGUIENTES PASOS**

#### **Credenciales de prueba**
- **Usuario Admin**: usuario: `admin`, contraseña: `adminpass`
- **Usuario Registrado**: usuario: `carlos`, contraseña: `pass`

### **Diagrama de Entidades de Base de Datos**

Diagrama mostrando las entidades, sus campos y relaciones:

![Diagrama Entidad-Relación](images/database-diagram.png)

> [Descripción opcional: Ej: "El diagrama muestra las 4 entidades principales: Usuario, Producto, Pedido y Categoría, con sus respectivos atributos y relaciones 1:N y N:M."]

### **Diagrama de Clases y Templates**

Diagrama de clases de la aplicación con diferenciación por colores o secciones:

![Diagrama de Clases](images/classes-diagram.png)

> [Descripción opcional del diagrama y relaciones principales]

### **Participación de Miembros en la Práctica 2**

#### **Alumno 1 - Aarón Fernández Lijó**

Mis principales tareas han estado centradas en la implementación completa de la seguridad de la aplicación con Spring Security, incluyendo autenticación, gestión de sesiones, control de accesos por roles y configuración del filtro de seguridad. Además, he trabajado en la protección CSRF de formularios, configuración de HTTPS con certificado autofirmado, creación de páginas de error personalizadas y corrección de accesos públicos a rutas y recursos estáticos.

| Nº | Commits | Files |
|:--:|:--------|:------|
|1| [Managed Session With Spring Security, Filter Chain added](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/d21c2496527be4db758b8c27c9d059690513ac10) | [SecurityConfiguration.java](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/java/es/codeujrc/distribuidos/security/SecurityConfiguration.java), [UserService.java](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/java/es/codeujrc/distribuidos/service/UserService.java) |
|2| [Enable CSRF protection and secure POST forms](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/cb7f546) | [login.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/resources/templates/login.html), [editUser.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/resources/templates/editUser.html) |
|3| [Enable HTTPS with self-signed certificate](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/48fb1c0) | [application.properties](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/resources/application.properties), [keystore.p12](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/resources/keystore.p12) |
|4| [Add custom error pages for 404, 403 and 500](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/b396b91) | [error.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/tree/main/distribuidos/src/main/resources/templates), [header.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/resources/templates/header.html) |
|5| [Fix public access to social page and deck images](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/977d1cc) | [SecurityConfiguration.java](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/java/es/codeujrc/distribuidos/security/SecurityConfiguration.java), [DecksController.java](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/main/distribuidos/src/main/java/es/codeujrc/distribuidos/controller/DecksController.java) |
---

#### **Alumno 2 - [Pablo León Molero]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [EditCard](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/aa2d24739879037c805b12332f83d4f6d20fd633)  | [AdminCard](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/aa2d24739879037c805b12332f83d4f6d20fd633#diff-47633d3c6d969d9daa1f5967c43237c86f4ca579ee271507cb4dcb457eae3cab)   |
|2| [CardImage](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/297bda23a149f9783520f8040354bbf289f9eff3)  | [CardController](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/297bda23a149f9783520f8040354bbf289f9eff3#diff-5d30feaff8710ce46e0d02f4c5eca5a8750aa02a0a3419774695b701d7daadcc)   |
|3| [AddCards](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/5511f59994ff1ac2f4039d01d7beb3a418691b3c)  | [admincard.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/5511f59994ff1ac2f4039d01d7beb3a418691b3c#diff-47633d3c6d969d9daa1f5967c43237c86f4ca579ee271507cb4dcb457eae3cab)   |
|4| [CardDetail](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/dbe326e51c039e68a9fd0fe3608589164b7efac9)  | [cardDetail.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/dbe326e51c039e68a9fd0fe3608589164b7efac9#diff-eb3124e9a814a372417851bc07e3722fbae88ff33d88f1e250ae02ba2927562c)   |
|5| [Metacards](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/commit/1c8979df759294aea45f401391ed771be414c2c0)  | [home.html](https://github.com/CodeURJC-SSDD-2025-26/practica-ssdd-2025-26-grupo-9/blob/1c8979df759294aea45f401391ed771be414c2c0/distribuidos/src/main/resources/templates/home.html)  |

---

#### **Alumno 3 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

#### **Alumno 4 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

## 🛠 **Práctica 3: API REST, docker y despliegue**

### **Documentación de la API REST**

#### **Especificación OpenAPI**
📄 **[Especificación OpenAPI (YAML)](/api-docs/api-docs.yaml)**

#### **Documentación HTML**
📖 **[Documentación API REST (HTML)](https://raw.githack.com/[usuario]/[repositorio]/main/api-docs/api-docs.html)**

> La documentación de la API REST se encuentra en la carpeta `/api-docs` del repositorio. Se ha generado automáticamente con SpringDoc a partir de las anotaciones en el código Java.

### **Diagrama de Clases y Templates Actualizado**

Diagrama actualizado incluyendo los @RestController y su relación con los @Service compartidos:

![Diagrama de Clases Actualizado](images/complete-classes-diagram.png)

### **Instrucciones de Ejecución con Docker**

#### **Requisitos previos:**
- Docker instalado (versión 20.10 o superior)
- Docker Compose instalado (versión 2.0 o superior)

#### **Pasos para ejecutar con docker-compose:**

1. **Clonar el repositorio** (si no lo has hecho ya):
   ```bash
   git clone https://github.com/[usuario]/[repositorio].git
   cd [repositorio]
   ```

2. **AQUÍ LOS SIGUIENTES PASOS**:

### **Construcción de la Imagen Docker**

#### **Requisitos:**
- Docker instalado en el sistema

#### **Pasos para construir y publicar la imagen:**

1. **Navegar al directorio de Docker**:
   ```bash
   cd docker
   ```

2. **AQUÍ LOS SIGUIENTES PASOS**

### **Despliegue en Máquina Virtual**

#### **Requisitos:**
- Acceso a la máquina virtual (SSH)
- Clave privada para autenticación
- Conexión a la red correspondiente o VPN configurada

#### **Pasos para desplegar:**

1. **Conectar a la máquina virtual**:
   ```bash
   ssh -i [ruta/a/clave.key] [usuario]@[IP-o-dominio-VM]
   ```
   
   Ejemplo:
   ```bash
   ssh -i ssh-keys/app.key vmuser@10.100.139.XXX
   ```

2. **AQUÍ LOS SIGUIENTES PASOS**:

### **URL de la Aplicación Desplegada**

🌐 **URL de acceso**: `https://[nombre-app].etsii.urjc.es:8443`

#### **Credenciales de Usuarios de Ejemplo**

| Rol | Usuario | Contraseña |
|:---|:---|:---|
| Administrador | admin | admin123 |
| Usuario Registrado | user1 | user123 |
| Usuario Registrado | user2 | user123 |

### **OTRA DOCUMENTACIÓN ADICIONAL REQUERIDA EN LA PRÁCTICA**

### **Participación de Miembros en la Práctica 3**

#### **Alumno 1 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

#### **Alumno 2 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

#### **Alumno 3 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

#### **Alumno 4 - [Nombre Completo]**

[Descripción de las tareas y responsabilidades principales del alumno en el proyecto]

| Nº    | Commits      | Files      |
|:------------: |:------------:| :------------:|
|1| [Descripción commit 1](URL_commit_1)  | [Archivo1](URL_archivo_1)   |
|2| [Descripción commit 2](URL_commit_2)  | [Archivo2](URL_archivo_2)   |
|3| [Descripción commit 3](URL_commit_3)  | [Archivo3](URL_archivo_3)   |
|4| [Descripción commit 4](URL_commit_4)  | [Archivo4](URL_archivo_4)   |
|5| [Descripción commit 5](URL_commit_5)  | [Archivo5](URL_archivo_5)   |

---

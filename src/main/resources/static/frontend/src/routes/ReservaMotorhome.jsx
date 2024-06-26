import { Link } from "react-router-dom";
import { Container, Button, Row, Col } from "reactstrap";
import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "./ReservaMotorhome.css";
import ImagenesModales from "../components/ImagenesModales";
import "react-datepicker/dist/react-datepicker.css";
import { useLocation } from "react-router-dom";
import Swal from "sweetalert2";

function addDays(date, days) {
  const result = new Date(date);
  result.setDate(result.getDate() + days);
  return result;
}

const ReservaMotorhome = () => {
  //******
  const { nombre } = useParams();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const fechaInicio = params.get("fechaInicio");
  console.log(fechaInicio);
  const fechaFin = params.get("fechaFin");
  console.log(fechaFin);

  const fechaInicioFormateada = addDays(
    new Date(fechaInicio),
    1
  ).toLocaleDateString("es-ES");
  const fechaFinFormateada = addDays(new Date(fechaFin), 1).toLocaleDateString(
    "es-ES"
  );

  console.log("Fecha de inicio:", fechaInicioFormateada);
  console.log("Fecha de fin:", fechaFinFormateada);
  //---------------

  const [imagenes, setImagenes] = useState([]);
  const [loading, setLoading] = useState(true); // Nuevo estado para controlar la carga de datos
  //---------------------
  const [producto1, setProducto1] = useState({
    descripcion: "",
    categorias: [],
  });

  const [usuarioReserva, setUsuarioReserva] = useState({});
  const [productoReserva, setProductoReserva] = useState({});
  const [usuario, setUsuario] = useState(
    useState(localStorage.getItem("usuario"))
  );
  const [email, setEmail] = useState(localStorage.getItem("email"));
  const auxUsuario = localStorage.getItem("usuario");
  useEffect(() => {
    axios
      .get(`http://localhost:8080/usuarios/buscar/` + auxUsuario)
      .then((res) => {
        // Procesar la respuesta si es necesario
        // Swal.fire(
        //   "¡Reserva Registrada!",
        //   "La Reserva se ha realizado con éxito.",
        //   "success"
        // );
        console.log(res.data);
        setUsuarioReserva(res.data);
      })
      .catch((error) => {
        console.error("Error al registrar los datos de la reserva", error);
        // Swal.fire(
        //   "¡Error!",
        //   "Hubo un problema al registrar la reserva.",
        //   "error"
        // );
      });
  }, []);
  useEffect(() => {
    axios
      .get(`http://localhost:8080/motorhome/detalle/` + nombre)
      .then((res) => {
        // Procesar la respuesta si es necesario
        // Swal.fire(
        //   "¡Reserva Registrada!",
        //   "La Reserva se ha realizado con éxito.",
        //   "success"
        // );
        console.log(res.data);
        setProductoReserva(res.data);
      })
      .catch((error) => {
        console.error("Error al registrar los datos de la reserva", error);
        // Swal.fire(
        //   "¡Error!",
        //   "Hubo un problema al registrar la reserva.",
        //   "error"
        // );
      });
  }, []);

  //--------------------
  const [showCalendar, setShowCalendar] = useState(false);

  const toggleCalendar = () => {
    setShowCalendar(!showCalendar);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [imagenesResponse, productoResponse] = await Promise.all([
          axios.get(`http://localhost:8080/imagenes/buscarPorProducto?`, {
            params: { nombre: nombre },
          }),
          axios.get(`http://localhost:8080/motorhome/detalle/${nombre}`),
        ]);

        const imagenesSeparadas = imagenesResponse.data[0].file.split("-");
        setImagenes(imagenesSeparadas);
        setProducto1(productoResponse.data);
        setLoading(false);
      } catch (error) {
        console.error(error);
        setLoading(false);
      }
    };

    fetchData();
  }, [nombre, fechaInicio, fechaFin]);

  if (loading) {
    // Mientras los datos se cargan, puedes mostrar un mensaje de carga o spinner
    return <div>Cargando...</div>;
  }

  const items = imagenes.map((imagen, index) => ({
    src: `../images/${imagen}`,
    key: index,
  }));

  const handleReserveClick = () => {
    // Objeto que contiene la información de la reserva
    const reservaData = {
      fechaIni: fechaInicio,
      fechaFin: fechaFin,
      nombre: nombre,
      usuario: usuarioReserva,
      producto: productoReserva,
    };
    console.log(reservaData);
    axios
      .post("http://localhost:8080/reserva/registrar", reservaData)
      .then((response) => {
        // Manejar la respuesta del servidor si es necesario
        console.log("Reserva realizada con éxito:", response.data);
        // Podrías redirigir al usuario a una página de confirmación o realizar otras acciones aquí
      })
      .catch((error) => {
        // Manejar errores si la reserva no se pudo completar
        console.error("Error al realizar la reserva:", error);
        // Mostrar mensajes de error, etc.
      });
  };

  console.log(items);

  const imagenesPrincipales = items.slice(0, 1); // La primera imagen es la principal.
  console.log("principal" + imagenesPrincipales);
  const imagenesSecundarias = items.slice(1, 5); // Máximo de 4 imágenes secundarias.
  console.log(producto1.categorias);
  console.log(producto1.caracteristicas);

  return (
    <Container fluid={false}>
      {/* Encabezado del Producto */}
      <Row className="tituloContainer">
        <Col className="columna" sm={{ size: 6 }}>
          <h2>Reserva del {nombre}</h2>
        </Col>
        <Col className="columna" sm={{ size: 4 }}>
          <Link to="/">
            <Button color="secondary" className="--bs-orange">
              Volver a Inicio
            </Button>
          </Link>
        </Col>
      </Row>
      <Row>
        <Col className="datos-usuario">
          <h3>Tus Datos de tu Reserva:</h3>
          <div className="datos-reserva">
            <Col>
              <p>NOMBRE: {usuario}</p>
              <p>E-MAIL: {email}</p>
            </Col>
            <Col>
              <p>FECHA DE RECOGIDA: {fechaInicioFormateada}</p>
              <p>FECHA DE ENTREGA: {fechaFinFormateada}</p>
            </Col>
            <Col>
              <div onClick={handleReserveClick}>
                <Button
                  color="secondary"
                  style={{
                    backgroundColor: "#FF8057",
                    border: "2px solid brown",
                    fontWeight: "bold",
                  }}
                  className="--bs-orange"
                >
                  CONFIRMAR LA RESERVAR
                </Button>
              </div>
            </Col>
          </div>
        </Col>
      </Row>

      {/* Imagenes del Producto */}
      <Row className="imagenesContainer">
        <Col className="imagenPrincipal">
          <img src={imagenesPrincipales[0].src} alt="Imagen principal" />
        </Col>

        <Col className="imagenesSecundarias">
          <Row>
            {imagenesSecundarias.map((imagen, index) => (
              <Col key={index} sm="6" md="6" lg="6">
                <img src={imagen.src} alt={`Imagen ${index + 1}`} />
              </Col>
            ))}
          </Row>
          <ImagenesModales nombre={nombre} />
        </Col>
      </Row>

      {/* Descripción del Producto */}
      {/* {producto1 && producto1.descripcion && */}
      <hr />
      <div className="descripcionContainer">
        <h3>Descripción:</h3>
        <p>{producto1.descripcion}</p>
      </div>
      {/* } */}
      <hr />
      <div className="categoriasContainer">
        <h3>Categorías:</h3>
        <ul>
          {producto1.categorias.map((categoria, index) => (
            <li key={index}>{categoria.nombre}</li>
          ))}
        </ul>
      </div>
      <hr />
      <div className="caracteristicasContainer">
        <h3>Características:</h3>
        <div className="caracteristicasList">
          {producto1.caracteristicas.map((caracteristica, index) => (
            <div className="caracteristicaItem" key={index}>
              <img
                className="icono"
                src={`../images/${caracteristica.file}`}
                alt=""
                style={{ width: "30px", height: "auto" }}
              />
              <p>{caracteristica.descripcion}</p>
            </div>
          ))}
        </div>
      </div>

      <hr />
    </Container>
  );
};

export default ReservaMotorhome;

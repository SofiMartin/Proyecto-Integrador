import { Link, useNavigate, useParams } from "react-router-dom";
import { Container, Button, Row, Col } from "reactstrap";
import { useState, useEffect, useRef } from "react";
import axios from "axios";
import PropTypes from "prop-types";
import "./Produ.css";
import ImagenesModales from "../components/ImagenesModales";
import { DateRangePicker } from "rsuite";
import "react-datepicker/dist/react-datepicker.css";

const Produ = () => {
  const { nombre } = useParams();
  const [imagenes, setImagenes] = useState([]);
  const [loading, setLoading] = useState(true);
  const isAuthenticated = !!localStorage.getItem("token");
  const [unavailableDates, setUnavailableDates] = useState([]);
  const [dateRange, setDateRange] = useState([new Date(), new Date()]);
  const inputRef = useRef(null);
  const [politica, setPolitica] = useState([]);
  const [producto1, setProducto1] = useState({
    descripcion: "",
    categorias: [],
  });
  const [showCalendar, setShowCalendar] = useState(false);
  const [selectedRange, setSelectedRange] = useState(null);

  const toggleCalendar = () => {
    setShowCalendar(!showCalendar);
  };

  const handleSelect = (value) => {
    setDateRange(value);
    setSelectedRange(value);
  };

  // Función para deshabilitar fechas
  const disabledDate = (date) => {
    return unavailableDates.some(
      (unavailableDate) =>
        date >= addDays(unavailableDate.startDate, 1) &&
        date <= addDays(unavailableDate.endDate, 2)
    );
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
  }, [nombre]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/reserva/reservaPorProducto/${producto1.id}`)
      .then((response) => {
        const dates = response.data.map((reserva) => ({
          startDate: new Date(reserva.fechaIni),
          endDate: new Date(reserva.fechaFin),
        }));
        setUnavailableDates(dates);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener fechas no disponibles:", error);
      });
  }, [producto1.id]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/politica")
      .then((res) => {
        setPolitica(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  if (loading) {
    return <div>Cargando...</div>;
  }

  const items = imagenes.map((imagen, index) => ({
    src: `../images/${imagen}`,
    key: index,
  }));

  const imagenesPrincipales = items.slice(0, 1);
  const imagenesSecundarias = items.slice(1, 5);

  const BotonReserva = ({ isAuthenticated, productoNombre }) => {
    const navigate = useNavigate();

    const handleReserveClick = () => {
      if (!isAuthenticated) {
        navigate(
          `/iniciarSesion?mensaje= El login es obligatorio para hacer una Reserva. Por favor, iniciá sesión.`
        );
      } else {
        if (selectedRange) {
          navigate(
            `/reservaMotorhome/${productoNombre}?fechaInicio=${selectedRange[0].toISOString()}&fechaFin=${selectedRange[1].toISOString()}`
          );
        } else {
          console.error("Por favor, selecciona un rango de fechas.");
        }
      }
    };

    return (
      <Col>
        <div onClick={handleReserveClick}>
          <Button
            color="secondary"
            style={{ backgroundColor: "#FF8057", border: "none" }}
            className="--bs-orange"
          >
            RESERVAR
          </Button>
        </div>
      </Col>
    );
  };

  return (
    <Container fluid={false}>
      <Row className="tituloContainer">
        <Col className="columna" sm={{ size: 6 }}>
          <h2>Detalle del {nombre}</h2>
        </Col>
        <Col className="columna" sm={{ size: 4 }}>
          <Link to="/">
            <Button color="secondary" className="--bs-orange">
              Volver a Inicio
            </Button>
          </Link>
        </Col>
      </Row>

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

      <hr />

      <div className="descripcionContainer">
        <h3>Descripción:</h3>
        <p>{producto1.descripcion}</p>
      </div>

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

      <Row className="verDisponibilidadContainer">
        <h3>Disponibilidad y Reserva:</h3>
        <Col xs="auto">
          <Button onClick={toggleCalendar} color="primary">
            Ver Disponibilidad
          </Button>
        </Col>
        <Col>
          {showCalendar && (
            <div
              style={{ position: "relative", width: "300px" }}
              ref={inputRef}
            >
              <DateRangePicker
                value={dateRange}
                onChange={(value) =>
                  handleSelect([addDays(value[0], -1), addDays(value[1], -1)])
                }
                format="yyyy-MM-dd"
                placement="bottom"
                shouldDisableDate={disabledDate} // Utiliza la función para deshabilitar fechas
              />
            </div>
          )}
        </Col>
        <Col>
          <BotonReserva
            isAuthenticated={isAuthenticated}
            productoNombre={producto1.nombre}
          />
        </Col>
      </Row>

      <hr />

      <h3>
        <u>Políticas de Uso:</u>{" "}
      </h3>

      <div className="politicasContainer">
        {politica.map((politica, index) => (
          <div key={index} className="politicaItem">
            <h5 style={{ textAlign: "center", height: "50px" }}>
              {politica.nombre}
            </h5>
            <p className="desc-poli">{politica.descripcion}</p>
          </div>
        ))}
      </div>
    </Container>
  );
};

function addDays(date, days) {
  const result = new Date(date);
  result.setDate(result.getDate() + days);
  return result;
}

Produ.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  productoNombre: PropTypes.string.isRequired,
};

export default Produ;

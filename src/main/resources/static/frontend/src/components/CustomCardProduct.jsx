import {
  Card,
  CardText,
  CardHeader,
  CardBody,
  CardTitle,
  CardSubtitle,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
} from "reactstrap";
import PropTypes from "prop-types";
import "./CustomCardProduct.css";
import SliderCardImage from "./SliderCardImage";
import { Link } from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";
import FavoriteIcon from "@mui/icons-material/Favorite";
import IconButton from "@mui/material/IconButton";
import { useUsuario } from "../contexts/UsuarioContext";
import ShareIcon from "@mui/icons-material/Share";
import FacebookIcon from "@mui/icons-material/Facebook";
import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import WhatsAppIcon from "@mui/icons-material/WhatsApp";
import { Helmet } from "react-helmet";
function CustomCardProduct({ producto }) {
  const [imagenes, setImagenes] = useState([]);
  const [modal, setModal] = useState(false);
  const { favs, setFavs } = useUsuario();

  const findChar = favs.find((fav) => fav.id === producto.id);

  const toggleModal = () => {
    setModal(!modal);
  };

  const addFav = () => {
    if (findChar) {
      const deleteFav = favs.filter((fav) => fav.id !== producto.id);
      setFavs(deleteFav);
    } else {
      //dispatch({ type: "ADD_FAV", payload: dent });
      setFavs([...favs, producto]); // Agrega el nuevo producto a la lista de favoritos
    }
  };

  useEffect(() => {
    axios
      .get(` http://localhost:8080/imagenes/buscarPorProducto? `, {
        params: { nombre: producto.nombre },
      })
      .then((res) => {
        const imagenesSeparadas = res.data[0].file.split("-");
        setImagenes(imagenesSeparadas);
      })
      .catch((err) => console.log(err));
  }, [producto]);

  const abrirEnlace = (enlace) => {
    window.open(enlace, "_blank");
    toggleModal();
  };

  const compartirEnFacebook = () => {
    abrirEnlace(
      `https://www.facebook.com/sharer/sharer.php?u=${window.location.href}producto/${producto.nombre}`
    );
  };

  const compartirEnInstagram = () => {
    abrirEnlace(`https://www.instagram.com/?url=${window.location.href}`);
  };

  const compartirEnTwitter = () => {
    // const message = `¡Hola! Echa un vistazo a este producto: ${producto.nombre}. ${window.location.href}producto/${producto.nombre}`;
    // const encodedMessage = encodeURIComponent(message);
    // const whatsappLink = `https://twitter.com/intent/tweet?url=${encodedMessage}`;

    abrirEnlace(
      `https://twitter.com/intent/tweet?url=http://localhost:5173/producto/${producto.nombre}`
    );
    // window.open(whatsappLink);
  };
  const compartirEnWhatsapp = () => {
    const message = `¡Hola! Echa un vistazo a este producto: ${producto.nombre}. http://localhost:5173/producto/${producto.nombre}`;
    const encodedMessage = encodeURIComponent(message);

    // Enlace que contiene la imagen, la descripción y el enlace
    const whatsappLink = `https://wa.me/?text=${encodedMessage}`;

    // Abre una nueva ventana con el enlace de WhatsApp
    window.open(whatsappLink);
  };

  return (
    <>
      <Helmet>
        <meta property="og:title" content={producto.nombre} />
        <meta property="og:description" content={producto.descripcion} />
        <meta property="og:image" content={`images/${imagenes[0]}`} />
        <meta property="og:url" content={window.location.href} />

        <meta name="twitter:title" content={producto.nombre} />
        <meta name="twitter:description" content={producto.descripcion} />
        <meta name="twitter:image" content={`images/${imagenes[0]}`} />
        <meta name="twitter:card" content="summary_large_image" />
      </Helmet>
      <Card className="customPro">
        {/* Botones del header de la card */}
        <CardHeader>
          <Button
            className="share-btn-principal --bs-orange"
            color="primary"
            size="sm"
            onClick={toggleModal}
          >
            <ShareIcon />
          </Button>
          <Modal isOpen={modal} toggle={toggleModal}>
            <ModalHeader toggle={toggleModal}>Compartir producto</ModalHeader>
            <ModalBody className="social-buttons">
              <img
                className="imagenmodal"
                src={`images/${imagenes[0]}`}
                alt="Imagen producto"
              />
              <p>{producto.descripcion}</p>
              <p>
                <a href={`${window.location.href}producto/${producto.nombre}`}>
                  {window.location.href}producto/${producto.nombre}
                </a>
              </p>
              <Button
                className="share-btn --bs-orange"
                color="primary"
                onClick={compartirEnFacebook}
              >
                <FacebookIcon />
              </Button>
              <Button
                className="share-btn --bs-orange"
                color="primary"
                onClick={compartirEnInstagram}
              >
                <InstagramIcon />
              </Button>
              <Button
                className="share-btn --bs-orange"
                color="primary"
                onClick={compartirEnTwitter}
              >
                <TwitterIcon />
              </Button>
              <Button
                className="share-btn --bs-orange"
                color="primary"
                onClick={compartirEnWhatsapp}
              >
                <WhatsAppIcon />
              </Button>
            </ModalBody>
          </Modal>
          <IconButton
            sx={{
              backgroundColor: "#B0A695",
              width: "auto",
              padding: "5.5px 8px",
              borderRadius: "5px",
            }}
            onClick={addFav}
            disableRipple
          >
            {findChar ? (
              <FavoriteIcon sx={{ color: "#D80032" }} />
            ) : (
              <FavoriteIcon sx={{ color: "#FFFFFF" }} />
            )}
          </IconButton>
        </CardHeader>

        {/* Body de la card */}
        <Link to={`/producto/${producto.nombre}`}>
          <SliderCardImage src={Object.values(imagenes)} alt="Card image cap" />
          <CardBody>
            <CardTitle tag="h5">{producto.nombre}</CardTitle>
            <CardSubtitle tag="h6" className="mb-2 text-muted">
              {producto.categorias.map((categoria) => (
                <span key={categoria.id}>{categoria.nombre} </span>
              ))}
            </CardSubtitle>
            <CardText className="descripciónproducto">
              {producto.descripcion}
            </CardText>
          </CardBody>
        </Link>
      </Card>
    </>
  );
}

CustomCardProduct.propTypes = {
  producto: PropTypes.object.isRequired,
};

export default CustomCardProduct;

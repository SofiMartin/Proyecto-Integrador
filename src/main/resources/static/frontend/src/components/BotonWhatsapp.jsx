import { Button } from "reactstrap";
import "./BotonWhatsapp.css";
import Swal from 'sweetalert2'

function BotonWhatsapp() {
  const compartirEnWhatsapp = () => {
    const enlace = `https://api.whatsapp.com/send?phone=34675344989&text=Hola, quería consultar por un Motorhome!`;
    abrirEnlace(enlace);
  };

  const abrirEnlace = (enlace) => {
    try {
      const newWindow = window.open(enlace, "_blank");
      if (!newWindow || newWindow.closed || typeof newWindow.closed === 'undefined') {
        throw new Error('Popup blocked!');
      }
      toggleModal();
      Swal.fire({
        title: "Gracias por tu consulta!",
        text: "El mensaje se envió correctamente",
        icon: "success"
      });
    } catch (err) {
      console.error(err);
      Swal.fire({
        title: "Error!",
        text: "Failed to open WhatsApp. Please enable pop-ups for this site.",
        icon: "error"
      });
    }
  };

  const toggleModal = () => {
    // Implement your modal toggle logic here if needed
  };

  return (
    <div className="divWhatsapp">
      <Button
        className="btnwhatsapp"
        onClick={compartirEnWhatsapp}
      >
        <img src="/images/whatsapplogo.png" className="whatsapp" alt="" />
      </Button>
    </div>
  );
}

export default BotonWhatsapp;



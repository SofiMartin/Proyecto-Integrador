import {Button} from "reactstrap"
import { Link } from "react-router-dom";

function BotonesIniciarSesión() {

    return (
        <div>
            <Link to="/iniciarSesion">
                <Button outline color="secondary" className="fixed-height">
                    Iniciar Sesión
                </Button>
            </Link>
            <Link to="/registrar">    
                <Button
                    color="primary"
                    className="sign-up-button fixed-height --bs-orange"
                >
                    Crear Cuenta
                </Button>
            </Link>
        </div>
    );
}
export default BotonesIniciarSesión;
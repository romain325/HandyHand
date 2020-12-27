import React from 'react';
import { Link } from 'react-router-dom';
import styles from './EnregistrementFeature.css';
import routes from '../../constants/routes.json';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ContentPage from '../../containers/ContentPage';

export default function EnregistrementFeature() {
  return (
    <ContentPage>
      <div>
        <Form>
          <h2>Enregistrement</h2>
          <h6>Crée un nouveau compte</h6>
          <Form.Group controlId="formBasicEmail">
            <Form.Control type="email" placeholder="Pseudo" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control type="password" placeholder="Mot de passe" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Confirmer mot de passe"
            />
          </Form.Group>
          <Link to={routes.HOME}>
            <Button type="submit" variant="primary" block>
              Créer un compte
            </Button>
          </Link>
          <Form.Text className="text-muted">
            Si vous avez déja un compte cliquez-<Link to={routes.CONNEXION}>ici</Link>
          </Form.Text>
        </Form>
      </div>
    </ContentPage>
  );
}

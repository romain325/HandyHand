import React from 'react';
import { Link } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';

export default function RegisterFeature() {
  return (
    <ContentPage childrenName="Register">
      <div>
        <Form>
          <h2>Enregistrement</h2>
          <h6>Créer un nouveau compte</h6>
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
            Si vous avez déja un compte cliquez-<Link to={routes.CONNECTION}>ici</Link>
          </Form.Text>
        </Form>
      </div>
    </ContentPage>
  );
}

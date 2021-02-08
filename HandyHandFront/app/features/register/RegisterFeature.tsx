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
          <h2>Register</h2>
          <h6>Create a new account</h6>
          <Form.Group controlId="formBasicEmail">
            <Form.Control type="email" placeholder="Mail" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control type="password" placeholder="Password please" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password again, just to be sure"
            />
          </Form.Group>
          <Link to={routes.HOME}>
            <Button type="submit" variant="primary" block>
              register now!
            </Button>
          </Link>
          <Form.Text className="text-muted">
            If you already have an account, <Link to={routes.CONNECTION}>Click Here !</Link>
          </Form.Text>
        </Form>
      </div>
    </ContentPage>
  );
}

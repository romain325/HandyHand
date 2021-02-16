import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useForm } from 'react-hook-form';
import styles from './ConnectionFeature.css';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import { UserCreds } from '../../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';
import { saveToken } from './Connexion';

export default function ConnectionFeature() {
  const { register, handleSubmit, errors } = useForm<UserCreds>();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const history = useHistory();

  const onSubmit = (data: UserCreds) => {
    setLoading(true);
    new HandyHandAPI()
      .connectUser(data)
      .then((r) => {
        saveToken(r);
        history.push(routes.MY_SCRIPT);
      })
      .catch((err) => {
        console.log(err);
        setError(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <ContentPage childrenName="Connexion">
      <div>
        <div className={styles.form}>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <h2>Connection</h2>
            <h6 className={error !== '' ? 'text-danger' : ''}>
              {error !== '' ? error : 'Login to your account'}
            </h6>
            <Form.Group controlId="formBasicEmail">
              <Form.Control
                type="email"
                placeholder="Email"
                id="mail"
                name="mail"
                ref={register({ required: true })}
              />
              {errors.mail && errors.mail.type === 'required' && (
                <div className="error">Your mail is mandatory</div>
              )}
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Control
                type="password"
                placeholder="Password"
                id="password"
                name="password"
                ref={register({ required: true })}
              />
              {errors.password && errors.password.type === 'required' && (
                <div className="error">Your password is mandatory</div>
              )}
            </Form.Group>

            <Button type="submit" variant="primary" block>
              { !loading ? "LogIn" : "Loading ... " }
            </Button>

            <Link to={routes.REGISTER}>
              <Button type="submit" variant="outline-primary" block>
                Register
              </Button>
            </Link>
          </Form>
        </div>
      </div>
    </ContentPage>
  );
}

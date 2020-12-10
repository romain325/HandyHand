import React from 'react';
import { Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../constants/routes.json';
import styles from './Home.css';

export default function Home(): JSX.Element {
  return (
    <div className={styles.container} data-tid="container">
      <h2>Home</h2>
      <div >
      <Link to={routes.COUNTER}>to Counter</Link>
      </div>
      <div>
      <Link to={routes.CONNEXION}>CONNEXION</Link>
      </div>
      <div>
      <Link to={routes.ENREGISTREMENT}>ENREGISTREMENT</Link>
      </div>
    </div>
  );
}

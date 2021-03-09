/* eslint react/jsx-props-no-spreading: off */
import React from 'react';
import { Switch, Route } from 'react-router-dom';
import * as routes from './constants/routes.json';
import App from './containers/App';
import HomeFeature from './features/home/HomeFeature';
import AddScriptFeature from './features/addScript/AddScriptFeature';
import ConnectionFeature from './features/connection/ConnectionFeature';
import RegisterFeature from './features/register/RegisterFeature';
import ScriptsFeatures from './features/scripts/ScriptsFeature';
import SettingsFeature from './features/settings/SettingsFeature';
import MyScriptsFeature from './features/myScripts/myScriptsFeature';
import ExecFeature from './features/execList/execList';
import GestureFeatures from './features/gestureList/gestureList';
import GestureDbFeatures from './features/gestureList/gestureListOnline';
import AddGestureFeature from './features/addGesture/AddGestureFeature';

export default function Routes() {
  return (
    <App>
      <Switch>
        <Route exact path={routes.HOME} component={HomeFeature} />
        <Route path={routes.CONNECTION} component={ConnectionFeature} />
        <Route path={routes.REGISTER} component={RegisterFeature} />
        <Route path={routes.SCRIPTS} component={ScriptsFeatures} />
        <Route path={routes.MY_SCRIPT} component={MyScriptsFeature} />
        <Route path={routes.ADD_SCRIPT} component={AddScriptFeature} />
        <Route path={routes.ADD_GESTURE} component={AddGestureFeature} />
        <Route path={routes.GESTURE} component={GestureFeatures} />
        <Route path={routes.GESTURE_DB} component={GestureDbFeatures} />
        <Route path={routes.EXEC} component={ExecFeature} />
        <Route path={routes.SETTINGS} component={SettingsFeature} />
      </Switch>
    </App>
  );
}

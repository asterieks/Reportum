// - Routes instead of RouteConfig
// - RouterModule instead of provideRoutes
import { Routes, RouterModule } from '@angular/router';

//import {AboutComponent} from './about/about.component';
import {HomeComponent} from './home/home.component';

const routes: Routes = [
{path: '',            component: HomeComponent },
{path: 'home',        component: HomeComponent },
//{path: '/about',       component: AboutComponent },
{path: '*',           component: HomeComponent }
];

// - Updated Export
export const routing = RouterModule.forRoot(routes);

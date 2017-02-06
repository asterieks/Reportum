// - Routes instead of RouteConfig
// - RouterModule instead of provideRoutes
import {Routes, RouterModule} from "@angular/router";
import {ManagerComponent} from "./manager/manager.component";
import {ReporterComponent} from "./reporter/reporter.component";

const routes: Routes = [
{path: '',            component: ReporterComponent },
{path: 'reporter',    component: ReporterComponent },
{path: 'manager',     component: ManagerComponent },
{path: '*',           component: ReporterComponent }
];

// - Updated Export
export const routing = RouterModule.forRoot(routes);

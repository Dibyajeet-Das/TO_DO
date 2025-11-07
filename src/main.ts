import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

//starting point of the program

//uses featre of ajava script polyfills

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
   

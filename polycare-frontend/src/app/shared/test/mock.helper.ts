import {
  BaseRequestOptions,
  Http,
  ResponseOptions,
  XHRBackend,
  Response
} from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

/**
 * Helper class to simplify mocking of other classes
 */
export class MockHelper {
  public static getProviders(service) {
    return [
      // default setup for making an HTTP req
      BaseRequestOptions,
      // for faking a backend
      MockBackend,
      service,
      {
        deps: [
          MockBackend,
          BaseRequestOptions
        ],
        provide: Http,
        // use factory to create Http instance
        useFactory: (xhrBackend: XHRBackend, defaultOptions: BaseRequestOptions) => {
          return new Http(xhrBackend, defaultOptions);
        }
      }
    ];
  }

  /**
   * Helper function to mock connection for a server
   * @param server
   * @param url
   * @param options
   */
  public static initConnection(url: string, server: MockBackend, options: any) {
    server.connections.subscribe((conn: MockConnection) => {
      if (conn.request.url === url) {
        const responseOptions = new ResponseOptions(options);

        conn.mockRespond(new Response(responseOptions));
      }
    });
  }
}

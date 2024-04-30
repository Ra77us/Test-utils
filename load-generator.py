import requests
import threading
import time

SLEEP = 1
N_REQUESTS = 15
URL = "http://localhost:8080/test/get-data-external"

def send_request():
    try:
        start = time.time()
        requests.get(URL)
        print(time.time() - start)
    except (KeyboardInterrupt, SystemExit) as e:
        raise e
    except Exception:
        pass

while True:
    for _ in range(N_REQUESTS):
        threading.Thread(target=send_request, daemon=True).start()
    time.sleep(SLEEP)

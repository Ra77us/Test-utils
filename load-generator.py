import requests
import threading
import time

SLEEP = 1000
N_REQUESTS = 30
URL = "http://localhost:8080/cpu/compute"

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

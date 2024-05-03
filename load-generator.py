import requests
import threading
import time

SLEEP = 15
N_REQUESTS = 6
URL = "http://149.156.182.229:30111/limited-threads/compute"

def send_request():
    try:
        start = time.time()
        res = requests.get(URL).content
        print(time.time() - start, res)
    except (KeyboardInterrupt, SystemExit) as e:
        raise e
    except Exception:
        pass

while True:
    for _ in range(N_REQUESTS):
        threading.Thread(target=send_request, daemon=True).start()
    time.sleep(SLEEP)
